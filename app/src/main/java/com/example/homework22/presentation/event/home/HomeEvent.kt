package com.example.homework22.presentation.event.home

sealed class HomeEvent {
    data object FetchStories : HomeEvent()
    data object FetchPosts : HomeEvent()
    data object ResetErrorMessage : HomeEvent()
    data class ItemClick(val id: Int) : HomeEvent()
}