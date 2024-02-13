package com.example.homework22.domain.model.post

data class GetPosts(
    val comments: Int,
    val id: Int,
    val images: List<String>,
    val likes: Int,
    val owner: GetOwner,
    val shareContent: String,
    val title: String
)