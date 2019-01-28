package com.chibde.weatherapp.di.module

import android.app.Application
import com.chibde.weatherapp.api.ConnectivityInterceptor
import com.chibde.weatherapp.api.RequestInterceptor
import com.chibde.weatherapp.api.WeatherApiService
import com.chibde.weatherapp.utils.LocationManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun getConnectivityInterceptor(app: Application): ConnectivityInterceptor {
        return ConnectivityInterceptor(app.applicationContext)
    }

    @Singleton
    @Provides
    fun getRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor()
    }

    @Singleton
    @Provides
    fun getRetrofitService(
        connectivityInterceptor: ConnectivityInterceptor,
        requestInterceptor: RequestInterceptor
    ): WeatherApiService {
        return WeatherApiService.invoke(
            connectivityInterceptor = connectivityInterceptor,
            requestInterceptor = requestInterceptor
        )
    }

    @Singleton
    @Provides
    fun getLocationManager(
        app: Application
    ): LocationManager {
        return LocationManager(
            LocationServices.getFusedLocationProviderClient(app)
        )
    }
}