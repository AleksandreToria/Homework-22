package com.example.homework22.data.mapper.story

import com.example.homework22.data.model.story.StoryDto
import com.example.homework22.domain.model.story.GetStories

fun StoryDto.toDomain() =
    GetStories(
        id = id,
        cover = cover,
        title = title
    )