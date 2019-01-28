package com.chibde.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("temp_c")
    val tempC: Double?
)