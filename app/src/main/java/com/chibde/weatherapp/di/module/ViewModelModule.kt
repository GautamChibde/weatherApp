package com.chibde.weatherapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chibde.weatherapp.di.ViewModelKey
import com.chibde.weatherapp.ui.WeatherViewModel
import com.chibde.weatherapp.utils.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@SuppressWarnings("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindsplashViewModel(splashViewModel: WeatherViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}