package com.example.homework22.domain.repository.story

import com.example.homework22.data.common.Resource
import com.example.homework22.domain.model.story.GetStories
import kotlinx.coroutines.flow.Flow

interface StoryRepository {
    suspend fun getStory(): Flow<Resource<List<GetStories>>>
}