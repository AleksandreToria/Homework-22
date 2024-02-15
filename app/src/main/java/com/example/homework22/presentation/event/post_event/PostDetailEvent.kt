package com.example.homework22.presentation.event.post_event

sealed class PostDetailEvent {
    data class FetchPostDetail(val id: Int) : PostDetailEvent()
    data object ItemClick: PostDetailEvent()
    data object ResetErrorMessage : PostDetailEvent()
}