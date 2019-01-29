package com.chibde.weatherapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chibde.weatherapp.api.WeatherApiService
import com.chibde.weatherapp.api.exceptions.BadRequestException
import com.chibde.weatherapp.api.exceptions.InternalServerException
import com.chibde.weatherapp.api.exceptions.NoConnectivityException
import com.chibde.weatherapp.model.WeatherForecast
import com.chibde.weatherapp.testing.OpenForTesting
import com.chibde.weatherapp.utils.LocationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class WeatherDataRepository @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val locationManager: LocationManager
) {
    private val _weatherDataResults = MutableLiveData<WeatherDataResults>()
    val weatherDataResults: LiveData<WeatherDataResults>
        get() = _weatherDataResults

    fun getWeatherData(days: Int = 4) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val location = locationManager.getFormatterLocationString()
                val weatherData = weatherApiService.getForecastAsync(
                    days = days,
                    location = location
                ).await()
                Log.i("data", "weather data = $weatherData")
                _weatherDataResults.postValue(
                    WeatherDataResults(
                        success = true,
                        data = weatherData
                    )
                )
            } catch (e: NoConnectivityException) {
                _weatherDataResults.postValue(
                    WeatherDataResults(
                        success = false,
                        error = "NoConnectivityException $e"
                    )
                )
            } catch (e: BadRequestException) {
                _weatherDataResults.postValue(
                    WeatherDataResults(
                        success = false,
                        error = "BadRequestException $e"
                    )
                )
            } catch (e: InternalServerException) {
                _weatherDataResults.postValue(
                    WeatherDataResults(
                        success = false,
                        error = "InternalServerException $e"
                    )
                )
            }
        }
    }
}

data class WeatherDataResults(
    val success: Boolean,
    val error: String = "",
    val data: WeatherForecast? = null
)