package com.chibde.weatherapp

import android.app.Application
import com.chibde.weatherapp.di.AppComponent
import com.chibde.weatherapp.di.DaggerAppComponent

class WeatherApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)
    }
}