package com.kguard.data

import android.renderscript.Element
import com.kguard.data.entity.WeatherEntity
import retrofit2.Retrofit
import javax.inject.Inject

class WeatherDataSourceImpl @Inject constructor(
    private val retrofit: Retrofit
): WeatherDataSource {
    override suspend fun getWeather(
        serviceKey: String,
        baseDate: String,
        baseTime: String,
        dataType: String,
        nx: Int,
        ny: Int
    ): WeatherEntity {
       return retrofit.create(WeatherDataSource::class.java).getWeather(serviceKey, baseDate, baseTime, dataType,nx, ny)
    }
}