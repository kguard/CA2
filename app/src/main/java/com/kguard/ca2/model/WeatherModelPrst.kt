package com.kguard.ca2.model

import com.kguard.ca2.uitl.RainType
import com.kguard.domain.WeatherModel
import java.time.LocalDateTime

data class WeatherModelPrst(
    val temp: String,
    val rainHour: String,
    val horizonWind : String,
    val verticalWind: String,
    val humidity: String,
    val rainType: RainType,
    val windDirection : String,
    val windSpeed: String,
    val now: String
)

fun WeatherModel.toWeatherModelPrst() : WeatherModelPrst{
    val rainType=when(rainType){
        "1"-> RainType.Rain
        "2"-> RainType.RainOrSnow
        "3"-> RainType.Snow
        "5"-> RainType.RainDrop
        "6"-> RainType.RainDropOrBlizzard
        "7"-> RainType.Blizzard
        else-> RainType.None
    }
    val now = LocalDateTime.now()

    val time = if (now.minute < 30) {
        if (now.hour > 9)
            "${now.hour}:00"
        else
            "0${now.hour}:00"
    } else {
        if (now.hour + 1 > 9) {
            "${now.hour + 1}:00"
        }
        else {
            "0${now.hour + 1}:00"
        }
    }
    return WeatherModelPrst(
        temp=temp,
        rainHour=rainHour,
        horizonWind=horizonWind,
        verticalWind=verticalWind,
        humidity=humidity,
        rainType=rainType,
        windDirection=windDirection,
        windSpeed=windSpeed,
        now=time
    )
}
