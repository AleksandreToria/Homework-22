package com.example.homework22

import com.example.homework22.data.common.Resource
import com.example.homework22.domain.model.post.GetOwner
import com.example.homework22.domain.model.post.GetPosts
import com.example.homework22.domain.model.story.GetStories
import com.example.homework22.domain.usecase.post.GetPostsUseCase
import com.example.homework22.domain.usecase.story.GetStoriesUseCase
import com.example.homework22.presentation.event.home.HomeEvent
import com.example.homework22.presentation.mapper.post.toPresenter
import com.example.homework22.presentation.mapper.story.toPresenter
import com.example.homework22.presentation.screen.home_fragment.HomeFragmentViewModel
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HomeFragmentViewModelTest {
    @Mock
    private lateinit var getStoriesUseCase: GetStoriesUseCase

    @Mock
    private lateinit var getPostsUseCase: GetPostsUseCase

    private lateinit var viewModel: HomeFragmentViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        viewModel = HomeFragmentViewModel(getStoriesUseCase, getPostsUseCase)
    }

    @Test
    fun `fetchStories emits correct states`() = runTest {
        // Arrange
        val stories = listOf(
            GetStories(id = 1, cover = "Aleksandre", title = "Longinoza"),
            GetStories(id = 2, cover = "Longinoza", title = "Longinoza"),
            GetStories(id = 3, cover = "Longinoza", title = "Longinoza")
        )
        whenever(getStoriesUseCase()).thenReturn(flowOf(Resource.Success(stories)))

        // Act
        viewModel.onEvent(HomeEvent.FetchStories)

        // Assert
        val collectedStates = viewModel.homeState.take(2).toList()

        assertTrue(
            "Expected at least one state with stories",
            collectedStates.any { it -> it.stories == stories.map { it.toPresenter() } && it.errorMessage == null }
        )
        assertFalse(
            "Expected isLoading to be false in last state",
            collectedStates.last().isLoading
        )
    }


    @Test
    fun `fetchPosts emits correct states`() = runTest {
        val posts = listOf(
            GetPosts(
                id = 1,
                comments = 100,
                images = listOf("dnasdksad", "daskndas"),
                likes = 100,
                owner = GetOwner("Zarapxana", "Mamqoria", 11, "dasan"),
                shareContent = "sakndas",
                title = "Sacodavi saqartvelo"
            ),
            GetPosts(
                id = 2,
                comments = 132100,
                images = listOf("dassddsa", "dasdsadasddasdkndas"),
                likes = 10230,
                owner = GetOwner("Epsia", "Direqtori", 11321321, "d21312321asan"),
                shareContent = "sa3213kndas",
                title = "Sac3123odavi saqart312321velo"
            )
        )
        whenever(getPostsUseCase()).thenReturn(flowOf(Resource.Success(posts)))

        viewModel.onEvent(HomeEvent.FetchPosts)

        val collectedStates = viewModel.homeState.take(2).toList()

        assertTrue(
            "Expected at least one state with posts",
            collectedStates.any { it -> it.posts == posts.map { it.toPresenter() } && it.errorMessage == null }
        )
        assertFalse(
            "Expected isLoading to be false in last state",
            collectedStates.last().isLoading
        )
    }


    @Test
    fun `fetchStories emits Error state`() = runTest {
        val errorMessage = "An error occurred"
        whenever(getStoriesUseCase()).thenReturn(flowOf(Resource.Error(errorMessage)))

        viewModel.onEvent(HomeEvent.FetchStories)

        val collectedStates = viewModel.homeState.take(2).toList()

        assertTrue(
            "Expected at least one state with error message",
            collectedStates.any { it.errorMessage == errorMessage }
        )
    }


    @Test
    fun `fetchPosts emits Error state`() = runTest {
        val errorMessage = "racxa erori"
        whenever(getPostsUseCase()).thenReturn(flowOf(Resource.Error(errorMessage)))

        viewModel.onEvent(HomeEvent.FetchPosts)

        val collectedStates = viewModel.homeState.take(2).toList()

        assertTrue(
            "Expected at least one state with error message",
            collectedStates.any { it.errorMessage == errorMessage }
        )
    }

    @Test
    fun `fetchStories emits Loading state`() = runTest {
        whenever(getStoriesUseCase()).thenReturn(flow {
            emit(Resource.Loading(true))
            emit(Resource.Success(emptyList()))
        })

        viewModel.onEvent(HomeEvent.FetchStories)

        val collectedStates = viewModel.homeState.take(2).toList()

        assertTrue(
            "Expected at least one state where isLoading is true",
            collectedStates.any { it.isLoading }
        )
    }


    @Test
    fun `fetchPosts emits Loading state`() = runTest {
        whenever(getPostsUseCase()).thenReturn(flow {
            emit(Resource.Loading(true))
            emit(Resource.Success(emptyList()))
        })

        viewModel.onEvent(HomeEvent.FetchPosts)

        val collectedStates = viewModel.homeState.take(2).toList()

        assertTrue(
            "Expected at least one state where isLoading is true",
            collectedStates.any { it.isLoading }
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}