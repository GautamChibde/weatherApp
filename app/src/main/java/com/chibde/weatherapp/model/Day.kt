package com.chibde.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("avgtemp_c")
    val avgtemp: Double?
)