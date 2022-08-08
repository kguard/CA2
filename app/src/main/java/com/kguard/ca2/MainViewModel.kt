package com.kguard.ca2

import android.location.Location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kguard.ca2.model.WeatherModelPrst
import com.kguard.ca2.uitl.Resource
import com.kguard.ca2.uitl.TransLocationUtil
import com.kguard.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: WeatherUseCase,
    private val tracker: LocationRepository
): ViewModel(){
    data class WeatherState(
        val weatherData: WeatherModelPrst?=null,
        val isLoading: Boolean=true,
        val error: String=""
    )
    private val _state:MutableStateFlow<WeatherState> = MutableStateFlow(WeatherState())
    val state: StateFlow<WeatherState> get() = _state.asStateFlow()
    private fun createRequestServiceKey(): String{return BuildConfig.SERVICE_KEY}

    private fun createRequestBaseTime(): String{
        val now = LocalTime.now()
        val baseTime = when  {
            now.hour > 11 -> {
                if (now.minute < 40) "${now.hour - 1}00"
                else  "${now.hour}00"
            }
            now.hour == 10 -> {
                if (now.minute < 40) "0900"
                else  "1000"
            }
            now.hour in 1..9 -> {
                if (now.minute < 40) "0${now.hour - 1}00"
                else  "0${now.hour}00"
            }
            now.hour == 0 -> {
                if (now.minute < 40) "2300"
                else  "0000"
            }
            else -> "0000"
        }
        return baseTime
    }

    private fun createRequestBaseDate():String{
        val now = LocalDateTime.now()
        val baseDate = if (now.hour != 0) {
            when {
                now.monthValue > 10 && now.dayOfMonth > 10 -> "${now.year}${now.monthValue}${now.dayOfMonth}"
                now.monthValue > 10 && now.dayOfMonth < 10 -> "${now.year}${now.monthValue}0${now.dayOfMonth}"
                now.monthValue < 10 && now.dayOfMonth > 10 -> "${now.year}0${now.monthValue}${now.dayOfMonth}"
                now.monthValue < 10 && now.dayOfMonth < 10 -> "${now.year}0${now.monthValue}0${now.dayOfMonth}"
                else -> "20220808"
            }
        } else {
            val date =
                if (createRequestBaseTime() != "0000") now.minusDays(1)
                else now

            when {
                date.monthValue > 10 && date.dayOfMonth > 10 -> "${date.year}${date.monthValue}${date.dayOfMonth}"
                date.monthValue > 10 && date.dayOfMonth < 10 -> "${date.year}${date.monthValue}0${date.dayOfMonth}"
                date.monthValue < 10 && date.dayOfMonth > 10 -> "${date.year}0${date.monthValue}${date.dayOfMonth}"
                date.monthValue < 10 && date.dayOfMonth < 10 -> "${date.year}0${date.monthValue}0${date.dayOfMonth}"
                else -> "20220808"
            }
        }
        return baseDate
    }
    private fun createRequestNx(location:Location):Int{
        return TransLocationUtil.convertLocation(location).nx.toInt()}
    private fun createRequestNy(location:Location):Int{
        return TransLocationUtil.convertLocation(location).ny.toInt()}

    fun getWeatherData(){
        viewModelScope.launch {
            _state.value= WeatherState(
                isLoading = true
            )
            tracker.getLocation()?.let { location ->
                useCase.invoke(createRequestServiceKey(),createRequestBaseDate(),createRequestBaseTime(),"JSON",createRequestNx(location),createRequestNy(location)).onEach {
                    result->
                    when(result){
                        is Resource.Success ->{
                            _state.value= WeatherState(
                                isLoading = false,
                                weatherData = result.data
                            )
                        }
                        is Resource.Error ->{
                            _state.value= WeatherState(
                                isLoading = false,
                                error = result.message ?:"알 수 없는 에러."
                            )
                        }
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

}