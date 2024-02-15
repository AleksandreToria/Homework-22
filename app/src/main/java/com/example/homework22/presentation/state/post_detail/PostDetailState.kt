package com.example.homework22.presentation.state.post_detail

import com.example.homework22.presentation.model.post.Posts

data class PostDetailState(
    val isLoading: Boolean = false,
    val postDetail: Posts? = null,
    val errorMessage: String? = null
)
