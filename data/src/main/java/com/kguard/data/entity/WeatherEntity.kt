package com.kguard.data.entity

import com.google.gson.annotations.SerializedName
import com.kguard.data.entity.Response
import com.kguard.domain.WeatherModel

data class WeatherEntity(
    val response: Response
)
fun WeatherEntity.toWeatherModel(): WeatherModel{
    val temp = response.body.items.item.filter { it.category == "T1H" }.map { it.obsrValue }.first()
    val rainHour = response.body.items.item.filter { it.category == "RN1" }.map { it.obsrValue }.first()
    val horizonWind = response.body.items.item.filter { it.category == "UUU" }.map { it.obsrValue }.first()
    val verticalWind = response.body.items.item.filter { it.category == "VVV" }.map { it.obsrValue }.first()
    val humidity = response.body.items.item.filter { it.category == "REH" }.map { it.obsrValue }.first()
    val rainType = response.body.items.item.filter { it.category == "PTY" }.map { it.obsrValue }.first()
    val windDirection = response.body.items.item.filter { it.category == "VEC" }.map { it.obsrValue }.first()
    val windSpeed = response.body.items.item.filter { it.category == "WSD" }.map { it.obsrValue }.first()
    return WeatherModel(
        temp, rainHour, horizonWind, verticalWind, humidity, rainType, windDirection, windSpeed
    )
}
