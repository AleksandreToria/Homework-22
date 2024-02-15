package com.example.homework22.domain.repository.post

import com.example.homework22.data.common.Resource
import com.example.homework22.domain.model.post.GetPosts
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPosts(): Flow<Resource<List<GetPosts>>>
    suspend fun getPostDetail(id: Int): Flow<Resource<GetPosts>>
}