package com.kguard.data

import com.kguard.data.entity.toWeatherModel
import com.kguard.domain.WeatherModel
import com.kguard.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val source: WeatherDataSource
): WeatherRepository {
    override suspend fun getWeather(serviceKey: String, baseDate: String, baseTime: String, dataType:String,nx: Int, ny: Int): WeatherModel {
        val weather = source.getWeather(serviceKey, baseDate, baseTime, dataType, nx, ny)
        return when(weather.response.header.resultCode){
            "00"->weather.toWeatherModel()
            "01"->throw WeatherApiException.ApplicationErrorException(weather.response.header.resultMsg)
            "02"->throw WeatherApiException.DbErrorException(weather.response.header.resultMsg)
            "03"->throw WeatherApiException.NoDataErrorException(weather.response.header.resultMsg)
            "04"->throw WeatherApiException.HttpErrorException(weather.response.header.resultMsg)
            "05"->throw WeatherApiException.ServiceTimeOutException(weather.response.header.resultMsg)
            "10"->throw WeatherApiException.InvalidRequestErrorException(weather.response.header.resultMsg)
            "11"->throw WeatherApiException.NoRequestErrorException(weather.response.header.resultMsg)
            "12"->throw WeatherApiException.NoOpenApiErrorException(weather.response.header.resultMsg)
            "20"->throw WeatherApiException.ServiceDeniedErrorException(weather.response.header.resultMsg)
            "21"->throw WeatherApiException.TemporarilyServiceKeyErrorException(weather.response.header.resultMsg)
            "22"->throw WeatherApiException.LimitedServiceErrorException(weather.response.header.resultMsg)
            "30"->throw WeatherApiException.NotRegisteredServiceKeyErrorException(weather.response.header.resultMsg)
            "31"->throw WeatherApiException.DeadLineErrorException(weather.response.header.resultMsg)
            "32"->throw WeatherApiException.UnregisteredIpErrorException(weather.response.header.resultMsg)
            "33"->throw WeatherApiException.UnsignedCallErrorException(weather.response.header.resultMsg)
            "99"->throw WeatherApiException.UnknownErrorException(weather.response.header.resultMsg)
            else->throw Throwable()
        }
    }
}