package com.example.homework22.domain.usecase.post

import javax.inject.Inject

data class PostUseCase @Inject constructor(
    val getPostsUseCase: GetPostsUseCase,
    val getPostDetailUseCase: GetPostDetailUseCase
)
