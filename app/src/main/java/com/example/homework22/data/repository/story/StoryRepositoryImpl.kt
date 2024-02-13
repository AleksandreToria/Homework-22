package com.example.homework22.data.repository.story

import com.example.homework22.data.common.HandleResponse
import com.example.homework22.data.common.Resource
import com.example.homework22.data.mapper.base.asResource
import com.example.homework22.data.mapper.story.toDomain
import com.example.homework22.data.service.story.StoryService
import com.example.homework22.domain.model.story.GetStories
import com.example.homework22.domain.repository.story.StoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val storyService: StoryService,
    private val handleResponse: HandleResponse
) : StoryRepository {
    override suspend fun getStory(): Flow<Resource<List<GetStories>>> {
        return handleResponse.apiCall {
            storyService.getStories()
        }.asResource {
            it.map { storyDto ->
                storyDto.toDomain()
            }
        }
    }
}