package com.example.homework22.presentation.state.home

import com.example.homework22.presentation.model.post.Posts
import com.example.homework22.presentation.model.story.Stories

data class HomeState(
    val isLoading: Boolean = false,
    val stories: List<Stories>? = null,
    val posts: List<Posts>? = null,
    val errorMessage: String? = null
)