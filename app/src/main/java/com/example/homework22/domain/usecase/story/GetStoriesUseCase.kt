package com.example.homework22.domain.usecase.story

import com.example.homework22.domain.repository.story.StoryRepository
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val storyRepository: StoryRepository
) {
    suspend operator fun invoke() = storyRepository.getStory()
}