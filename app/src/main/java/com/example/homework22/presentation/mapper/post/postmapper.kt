package com.example.homework22.presentation.mapper.post

import com.example.homework22.domain.model.post.GetOwner
import com.example.homework22.domain.model.post.GetPosts
import com.example.homework22.presentation.model.post.Owners
import com.example.homework22.presentation.model.post.Posts

fun GetOwner.toPresenter(): Owners = Owners(
    firstName = firstName,
    lastName = lastName,
    postDate = postDate,
    profile = profile
)

fun GetPosts.toPresenter(): Posts = Posts(
    comments = comments,
    id = id,
    images = images,
    likes = likes,
    owner = owner.toPresenter(),
    shareContent = shareContent,
    title = title
)