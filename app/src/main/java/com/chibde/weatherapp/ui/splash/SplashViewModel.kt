package com.chibde.weatherapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chibde.weatherapp.repository.WeatherDataRepository
import com.chibde.weatherapp.repository.WeatherDataResults
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val repository: WeatherDataRepository
) : ViewModel() {

    val dataResults: LiveData<WeatherDataResults>
        get() = repository.weatherDataResults


    fun getWeatherData() {
        repository.getWeatherData()
    }
}