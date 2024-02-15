package com.example.homework22.presentation.screen.home_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework22.data.common.Resource
import com.example.homework22.domain.usecase.post.PostUseCase
import com.example.homework22.domain.usecase.story.GetStoriesUseCase
import com.example.homework22.presentation.event.home.HomeEvent
import com.example.homework22.presentation.mapper.post.toPresenter
import com.example.homework22.presentation.mapper.story.toPresenter
import com.example.homework22.presentation.state.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val postUseCase: PostUseCase
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState: SharedFlow<HomeState> = _homeState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HomeFragmentUiEvent>()
    val uiEvent: SharedFlow<HomeFragmentUiEvent> get() = _uiEvent

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.FetchStories -> fetchStories()
            HomeEvent.ResetErrorMessage -> updateErrorMessage(message = null)
            HomeEvent.FetchPosts -> fetchPosts()
            is HomeEvent.ItemClick -> onClick(HomeFragmentUiEvent.NavigateToPostDetail(event.id))
        }
    }

    private fun fetchStories() {
        viewModelScope.launch {
            getStoriesUseCase().collect { it ->
                when (it) {
                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)

                    is Resource.Loading -> {
                        _homeState.update { currentState ->
                            currentState.copy(
                                isLoading = it.loading
                            )
                        }
                    }

                    is Resource.Success -> {
                        _homeState.update { currentState ->
                            currentState.copy(
                                stories = it.data.map {
                                    it.toPresenter()
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            postUseCase.getPostsUseCase().collect() { it ->
                when (it) {
                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)

                    is Resource.Loading -> {
                        _homeState.update { currentState ->
                            currentState.copy(
                                isLoading = it.loading
                            )
                        }
                    }

                    is Resource.Success -> {
                        _homeState.update { currentState ->
                            currentState.copy(
                                posts = it.data.map {
                                    it.toPresenter()
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onClick(homeFragmentUiEvent: HomeFragmentUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(homeFragmentUiEvent)
        }
    }

    private fun updateErrorMessage(message: String?) {
        _homeState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    sealed interface HomeFragmentUiEvent {
        data class NavigateToPostDetail(val id: Int) : HomeFragmentUiEvent
    }
}