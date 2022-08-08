package com.kguard.domain.repository

import com.kguard.domain.WeatherModel

interface WeatherRepository {
    suspend fun getWeather(serviceKey:String, baseDate:String, baseTime:String,dataType:String,nx:Int, ny:Int): WeatherModel
}