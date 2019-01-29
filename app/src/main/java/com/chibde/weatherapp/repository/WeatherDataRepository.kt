package com.chibde.weatherapp.repository

import com.chibde.weatherapp.api.WeatherApiService
import com.chibde.weatherapp.api.exceptions.BadRequestException
import com.chibde.weatherapp.api.exceptions.InternalServerException
import com.chibde.weatherapp.api.exceptions.NoConnectivityException
import com.chibde.weatherapp.model.WeatherForecast
import com.chibde.weatherapp.testing.OpenForTesting
import com.chibde.weatherapp.utils.LocationManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class WeatherDataRepository @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val locationManager: LocationManager
) {
    suspend fun getWeatherData(days: Int = 4): WeatherDataResults {
        try {
            val location = locationManager.getFormatterLocationString()
            val weatherData = weatherApiService.getForecastAsync(
                days = days,
                location = location
            ).await()

            return WeatherDataResults(
                success = true,
                data = weatherData
            )
        } catch (e: NoConnectivityException) {
            return WeatherDataResults(
                success = false,
                error = "NoConnectivityException $e"
            )
        } catch (e: BadRequestException) {
            return WeatherDataResults(
                success = false,
                error = "BadRequestException $e"

            )
        } catch (e: InternalServerException) {
            return WeatherDataResults(
                success = false,
                error = "InternalServerException $e"
            )
        } catch (e: IllegalStateException) {
            return WeatherDataResults(
                success = false,
                error = "IllegalStateException $e"
            )
        }
    }
}

data class WeatherDataResults(
    val success: Boolean,
    val error: String = "",
    val data: WeatherForecast? = null
)