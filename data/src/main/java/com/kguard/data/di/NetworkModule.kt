package com.kguard.data.di

import com.kguard.data.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(providOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun providOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(3,TimeUnit.SECONDS)
            .readTimeout(5,TimeUnit.SECONDS)
            .addInterceptor(provideOkHttpLogging())
            .build()
    }
    @Provides
    @Singleton
    fun provideOkHttpLogging():HttpLoggingInterceptor{
        return HttpLoggingInterceptor().apply {
            level=if(BuildConfig.DEBUG){
                HttpLoggingInterceptor.Level.BODY
            }
            else{
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }
}