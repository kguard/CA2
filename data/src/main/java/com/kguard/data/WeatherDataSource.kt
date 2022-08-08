package com.kguard.data

import com.kguard.data.entity.WeatherEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherDataSource {
    @GET("1360000/VilageFcstInfoService_2.0/getUltraSrtNcst")
    suspend fun getWeather(
        @Query("serviceKey")
        serviceKey:String,
        @Query("base_date")
        baseDate:String,
        @Query("base_time")
        baseTime:String,
        @Query("dataType")
        dataType:String,
        @Query("nx")
        nx:Int,
        @Query("ny")
        ny:Int
    ): WeatherEntity
}