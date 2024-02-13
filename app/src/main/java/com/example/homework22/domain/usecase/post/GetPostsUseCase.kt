package com.example.homework22.domain.usecase.post

import com.example.homework22.domain.repository.post.PostRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke() = postRepository.getPosts()
}