package com.example.homework22.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework22.data.common.Resource
import com.example.homework22.domain.usecase.post.GetPostsUseCase
import com.example.homework22.domain.usecase.story.GetStoriesUseCase
import com.example.homework22.presentation.event.HomeEvent
import com.example.homework22.presentation.mapper.post.toPresenter
import com.example.homework22.presentation.mapper.story.toPresenter
import com.example.homework22.presentation.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getStoriesUseCase: GetStoriesUseCase,
    private val getPostUseCase: GetPostsUseCase
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState())
    val homeState: SharedFlow<HomeState> = _homeState.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.FetchStories -> fetchStories()
            HomeEvent.ResetErrorMessage -> updateErrorMessage(message = null)
            HomeEvent.FetchPosts -> fetchPosts()
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
            getPostUseCase().collect() { it ->
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

    private fun updateErrorMessage(message: String?) {
        _homeState.update { currentState -> currentState.copy(errorMessage = message) }
    }
}