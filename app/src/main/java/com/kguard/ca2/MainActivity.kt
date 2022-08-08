package com.kguard.ca2

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.kguard.ca2.databinding.ActivityMainBinding
import com.kguard.ca2.uitl.RainType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}
    private val model: MainViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        permissionLauncher =registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){
            model.getWeatherData()
        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenResumed {
            model.state.collectLatest {
                binding.tempValue.text=it.weatherData?.temp+ "℃"
                binding.rainHourValue.text=it.weatherData?.rainHour+"mm"
                binding.horizonWindValue.text=it.weatherData?.horizonWind+"m/s"
                binding.verticalWindValue.text=it.weatherData?.verticalWind+"m/s"
                binding.humidityValue.text=it.weatherData?.humidity+"%"
                binding.rainTypeValue.text=when(it.weatherData?.rainType){
                    RainType.None ->{"없음"}
                    RainType.Rain->{"비"}
                    RainType.RainOrSnow->{"비/눈"}
                    RainType.Snow->{"눈"}
                    RainType.RainDrop->{"빗방울"}
                    RainType.RainDropOrBlizzard->{"빗방울/눈날림"}
                    RainType.Blizzard->{"눈날림"}
                    else -> {"에러"}
                }
                binding.windDirectionValue.text=it.weatherData?.windDirection+"deg"
                binding.windSpeedValue.text=it.weatherData?.windSpeed+"m/s"
                binding.timeValue.text=it.weatherData?.now
                if(it.error.isNotEmpty()) {
                    Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                    Log.e(javaClass.simpleName, "onResume:${it.error}",)
                }
            }
        }
    }
}