package com.kguard.data.di

import com.kguard.data.WeatherDataSource
import com.kguard.data.WeatherDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Provides
    @Singleton
    fun provideWeatherDataSource(retrofit: Retrofit):WeatherDataSource{
        return WeatherDataSourceImpl(retrofit)
    }
}