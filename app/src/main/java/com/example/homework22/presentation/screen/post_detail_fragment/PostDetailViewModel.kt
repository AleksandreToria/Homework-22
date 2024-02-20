package com.example.homework22.presentation.screen.post_detail_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homework22.data.common.Resource
import com.example.homework22.domain.usecase.post.GetPostDetailUseCase
import com.example.homework22.presentation.event.post_event.PostDetailEvent
import com.example.homework22.presentation.mapper.post.toPresenter
import com.example.homework22.presentation.state.post_detail.PostDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    private val getPostDetailUseCase: GetPostDetailUseCase
) : ViewModel() {
    private val _postDetailState = MutableStateFlow(PostDetailState())
    val postDetailState: SharedFlow<PostDetailState> = _postDetailState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<PostFragmentUiEvent>()
    val uiEvent: SharedFlow<PostFragmentUiEvent> get() = _uiEvent

    fun onEvent(event: PostDetailEvent) {
        when (event) {
            PostDetailEvent.ResetErrorMessage -> updateErrorMessage(message = null)
            is PostDetailEvent.FetchPostDetail -> fetchPostDetail(event.id)
            PostDetailEvent.ItemClick -> onClick(PostFragmentUiEvent.NavigateToHomeFragment)
        }
    }

    private fun fetchPostDetail(id: Int) {
        viewModelScope.launch {
            getPostDetailUseCase(id).collect {
                when (it) {
                    is Resource.Error -> updateErrorMessage(message = it.errorMessage)

                    is Resource.Loading -> {
                        _postDetailState.update { currentState ->
                            currentState.copy(
                                isLoading = it.loading
                            )
                        }
                    }

                    is Resource.Success -> {
                        _postDetailState.update { currentState ->
                            currentState.copy(
                                postDetail = it.data.toPresenter()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun onClick(postFragmentUiEvent: PostFragmentUiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(postFragmentUiEvent)
        }
    }

    private fun updateErrorMessage(message: String?) {
        _postDetailState.update { currentState -> currentState.copy(errorMessage = message) }
    }

    sealed interface PostFragmentUiEvent {
        data object NavigateToHomeFragment : PostFragmentUiEvent
    }
}