package com.chibde.weatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("current")
    val current: Current?,
    @SerializedName("forecast")
    val forecast: Forecast?,
    @SerializedName("location")
    val location: Location?
)