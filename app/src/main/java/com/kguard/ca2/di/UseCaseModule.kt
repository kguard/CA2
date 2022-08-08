package com.kguard.ca2.di

import com.kguard.ca2.WeatherUseCase
import com.kguard.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideWeatherUseCase(
        repository: WeatherRepository
    ): WeatherUseCase {
        return WeatherUseCase(repository)
    }
}