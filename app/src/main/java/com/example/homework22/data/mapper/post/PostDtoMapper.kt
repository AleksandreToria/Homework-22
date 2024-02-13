package com.example.homework22.data.mapper.post

import com.example.homework22.data.model.post.Owner
import com.example.homework22.data.model.post.PostDto
import com.example.homework22.domain.model.post.GetOwner
import com.example.homework22.domain.model.post.GetPosts

fun Owner.toDomain(): GetOwner =
    GetOwner(
        firstName = firstName ?: "",
        lastName = lastName ?: "",
        postDate = postDate ?: 0,
        profile = profile ?: ""
    )


fun PostDto.toDomain(): GetPosts = GetPosts(
    comments = comments ?: 0,
    id = id ?: 0,
    images = images ?: listOf(),
    likes = likes ?: 0,
    owner = owner?.toDomain() ?: GetOwner("", "", 0, ""),
    shareContent = shareContent ?: "",
    title = title ?: ""
)