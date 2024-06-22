package com.leodemo.taipei_tour_compose.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.leodemo.taipei_tour.data.local.sharePreference.ShareLocalDataSource
import com.leodemo.taipei_tour.data.local.sharePreference.SharePreferenceDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providePreferenceManager(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideShareLocalDataSource(
        sharedPreferences: SharedPreferences
    ): ShareLocalDataSource {
        return SharePreferenceDataSource(sharedPreferences)
    }
}