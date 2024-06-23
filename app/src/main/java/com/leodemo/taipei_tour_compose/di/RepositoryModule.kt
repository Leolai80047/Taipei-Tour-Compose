package com.leodemo.taipei_tour_compose.di

import com.leodemo.taipei_tour.data.api.AttractionApi
import com.leodemo.taipei_tour.data.local.sharePreference.ShareLocalDataSource
import com.leodemo.taipei_tour.data.repository.attraction.AttractionInteractor
import com.leodemo.taipei_tour.data.repository.attraction.AttractionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideAttractionRepository(
        attractionApi: AttractionApi,
        shareLocalDataSource: ShareLocalDataSource
    ): AttractionInteractor {
        return AttractionRepository(attractionApi, shareLocalDataSource)
    }
}