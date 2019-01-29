package com.chibde.weatherapp.di.module

import com.chibde.weatherapp.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeSplashActivity(): MainActivity
}