package com.kguard.ca2

import com.kguard.ca2.model.WeatherModelPrst
import com.kguard.ca2.model.toWeatherModelPrst
import com.kguard.ca2.uitl.Resource
import com.kguard.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
){
    suspend fun invoke(serviceKey: String, baseDate: String, baseTime: String, dataType:String,nx: Int, ny: Int): Flow<Resource<WeatherModelPrst>> =
        flow {
            try {
                val weatherModelPrst = repository.getWeather(serviceKey, baseDate, baseTime,dataType,nx, ny).toWeatherModelPrst()
                emit(Resource.Success<WeatherModelPrst>(weatherModelPrst))
            }catch (t: Exception){
                emit(Resource.Error<WeatherModelPrst>(t.message ?:""))
            }
        }
}