package com.example.homework22.di

import com.example.homework22.data.common.HandleResponse
import com.example.homework22.data.repository.post.PostRepositoryImpl
import com.example.homework22.data.repository.story.StoryRepositoryImpl
import com.example.homework22.data.service.post.PostService
import com.example.homework22.data.service.story.StoryService
import com.example.homework22.domain.repository.post.PostRepository
import com.example.homework22.domain.repository.story.StoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideStoryRepository(
        storyService: StoryService,
        handleResponse: HandleResponse
    ): StoryRepository {
        return StoryRepositoryImpl(
            storyService = storyService,
            handleResponse = handleResponse
        )
    }

    @Singleton
    @Provides
    fun providePostRepository(
        postService: PostService,
        handleResponse: HandleResponse
    ): PostRepository {
        return PostRepositoryImpl(
            postService = postService,
            handleResponse = handleResponse
        )
    }
}