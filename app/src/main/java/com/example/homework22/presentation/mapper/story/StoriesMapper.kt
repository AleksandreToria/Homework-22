package com.example.homework22.presentation.mapper.story

import com.example.homework22.domain.model.story.GetStories
import com.example.homework22.presentation.model.story.Stories

fun GetStories.toPresenter() =
    Stories(
        id = id,
        cover = cover,
        title = title
    )