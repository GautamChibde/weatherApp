package com.chibde.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday")
    val forecastDays: List<ForecastDay> = emptyList()
)