package com.example.homework22.domain.usecase.post

import com.example.homework22.domain.repository.post.PostRepository
import javax.inject.Inject

class GetPostDetailUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(id: Int) = postRepository.getPostDetail(id)
}