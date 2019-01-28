package com.chibde.weatherapp.utils

import android.annotation.SuppressLint
import com.chibde.weatherapp.testing.OpenForTesting
import com.google.android.gms.location.FusedLocationProviderClient

@OpenForTesting
class LocationManager(
    private val fusedLocationClient: FusedLocationProviderClient
) {

    suspend fun getFormatterLocationString(): String {
        val location = getLastLocationAsync().await()
        return "${location.latitude},${location.longitude}"
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocationAsync() = fusedLocationClient.lastLocation.asDeferred()
}