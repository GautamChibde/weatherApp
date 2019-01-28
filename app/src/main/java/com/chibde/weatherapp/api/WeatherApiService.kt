package com.chibde.weatherapp.api

import com.chibde.weatherapp.model.WeatherForecast
import com.chibde.weatherapp.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecast.json")
    fun getForecastAsync(
        @Query("days") days: Int,
        @Query("q") location: String
    ): Deferred<WeatherForecast>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor,
            requestInterceptor: RequestInterceptor
        ): WeatherApiService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }
}