package com.example.homework22.data.model.post

import com.squareup.moshi.Json

data class PostDto(
    val comments: Int?,
    val id: Int?,
    val images: List<String>?,
    val likes: Int?,
    val owner: Owner?,
    @Json(name = "share_content")
    val shareContent: String?,
    val title: String?
)