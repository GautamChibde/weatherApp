package com.chibde.weatherapp.di

import android.app.Application
import com.chibde.weatherapp.ui.main.MainActivity
import com.chibde.weatherapp.WeatherApp
import com.chibde.weatherapp.di.module.ActivityModule
import com.chibde.weatherapp.di.module.AppModule
import com.chibde.weatherapp.ui.splash.SplashActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: WeatherApp)
}