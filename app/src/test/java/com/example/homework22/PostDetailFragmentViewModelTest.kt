package com.example.homework22

import com.example.homework22.data.common.Resource
import com.example.homework22.domain.model.post.GetOwner
import com.example.homework22.domain.model.post.GetPosts
import com.example.homework22.domain.usecase.post.GetPostDetailUseCase
import com.example.homework22.presentation.event.post_event.PostDetailEvent
import com.example.homework22.presentation.mapper.post.toPresenter
import com.example.homework22.presentation.screen.post_detail_fragment.PostDetailViewModel
import com.example.homework22.presentation.state.post_detail.PostDetailState
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
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
class PostDetailFragmentViewModelTest {
    @Mock
    private lateinit var getPostDetailUseCase: GetPostDetailUseCase

    private lateinit var viewModel: PostDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        viewModel = PostDetailViewModel(getPostDetailUseCase)
    }

    @Test
    fun `fetchPostDetail emits correct states`() = runTest {
        val postId = 1
        val postDetail = GetPosts(
            id = postId,
            comments = 100,
            images = listOf("dnasdksad", "daskndas"),
            likes = 100,
            owner = GetOwner("Zarapxana", "Mamqoria", 11, "dasan"),
            shareContent = "sakndas",
            title = "Sacodavi saqartvelo"
        )
        whenever(getPostDetailUseCase(postId)).thenReturn(flowOf(Resource.Success(postDetail)))

        val collectedStates = mutableListOf<PostDetailState>()
        val collectJob = launch {
            viewModel.postDetailState.take(2).toList(collectedStates)
        }

        viewModel.onEvent(PostDetailEvent.FetchPostDetail(postId))
        collectJob.join()

        assertTrue(
            "Expected at least one state with postDetail",
            collectedStates.any { it.postDetail == postDetail.toPresenter() && it.errorMessage == null }
        )
        assertFalse(
            "Expected isLoading to be false after success",
            collectedStates.last().isLoading
        )
    }

    @Test
    fun `fetchPostDetail emits Error state`() = runTest {
        val errorMessage = "Error fetching post details"
        val postId = 1
        whenever(getPostDetailUseCase(postId)).thenReturn(flowOf(Resource.Error(errorMessage)))

        val collectedStates = mutableListOf<PostDetailState>()
        val collectJob = launch {
            viewModel.postDetailState.take(2).toList(collectedStates)
        }

        viewModel.onEvent(PostDetailEvent.FetchPostDetail(postId))
        collectJob.join()

        assertTrue(
            "Expected at least one state with error message",
            collectedStates.any { it.errorMessage == errorMessage }
        )
    }

    @Test
    fun `fetchPostDetail emits Loading state`() = runTest {
        val postId = 1
        whenever(getPostDetailUseCase(postId)).thenReturn(
            flowOf(
                Resource.Loading(true),
                Resource.Success(
                    GetPosts(
                        id = postId,
                        comments = 100,
                        images = listOf("image1", "image2"),
                        likes = 100,
                        owner = GetOwner("Zarapxana", "Mamqoria", 11, "dasan"),
                        shareContent = "Share this post",
                        title = "Post Title"
                    )
                )
            )
        )

        val collectedStates = mutableListOf<PostDetailState>()
        val collectJob = launch {
            viewModel.postDetailState.take(2).toList(collectedStates)
        }

        viewModel.onEvent(PostDetailEvent.FetchPostDetail(postId))
        collectJob.join()

        assertTrue(
            "Expected at least one state where isLoading is true",
            collectedStates.any { it.isLoading })
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}