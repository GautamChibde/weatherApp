package com.chibde.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chibde.weatherapp.repository.WeatherDataRepository
import com.chibde.weatherapp.repository.WeatherDataResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val repository: WeatherDataRepository
) : ViewModel() {

    private val _dataResults = MutableLiveData<WeatherDataResults>()
    val dataResults: LiveData<WeatherDataResults>
        get() = _dataResults


    fun getWeatherData() {
        GlobalScope.launch(Dispatchers.IO) {
            val weatherDataResults = repository.getWeatherData()
            _dataResults.postValue(weatherDataResults)
        }
    }
}