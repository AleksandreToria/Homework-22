package com.example.homework22.data.repository.post

import com.example.homework22.data.common.HandleResponse
import com.example.homework22.data.common.Resource
import com.example.homework22.data.mapper.base.asResource
import com.example.homework22.data.mapper.post.toDomain
import com.example.homework22.data.service.post.PostService
import com.example.homework22.domain.model.post.GetPosts
import com.example.homework22.domain.repository.post.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService,
    private val handleResponse: HandleResponse
) : PostRepository {
    override suspend fun getPosts(): Flow<Resource<List<GetPosts>>> {
         return handleResponse.apiCall {
             postService.getPosts()
         }.asResource {
             it.map { postDto ->
                 postDto.toDomain()
             }
         }
    }

    override suspend fun getPostDetail(id: Int): Flow<Resource<GetPosts>> {
        return handleResponse.apiCall {
            postService.getPostDetail(id)
        }.asResource {
            it.toDomain()
        }
    }
}