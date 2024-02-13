package com.example.homework22.presentation.model.post

data class Posts(
    val comments: Int,
    val id: Int,
    val images: List<String>,
    val likes: Int,
    val owner: Owners,
    val shareContent: String,
    val title: String
)
