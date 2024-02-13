package com.example.homework22.data.service.post

import com.example.homework22.data.model.post.PostDto
import retrofit2.Response
import retrofit2.http.GET

interface PostService {
    @GET("25caefd7-7e7e-4178-a86f-e5cfee2d88a0")
    suspend fun getPosts(): Response<List<PostDto>>
}