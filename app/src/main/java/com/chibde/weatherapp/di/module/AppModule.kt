package com.chibde.weatherapp.di.module

import android.app.Application
import com.chibde.weatherapp.api.ConnectivityInterceptor
import com.chibde.weatherapp.api.RequestInterceptor
import com.chibde.weatherapp.api.WeatherApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
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
}