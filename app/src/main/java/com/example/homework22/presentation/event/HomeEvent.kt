package com.example.homework22.presentation.event

sealed class HomeEvent {
    data object FetchStories : HomeEvent()
    data object FetchPosts : HomeEvent()
    data object ResetErrorMessage : HomeEvent()
}