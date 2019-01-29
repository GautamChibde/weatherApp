package com.chibde.weatherapp.di.module

import com.chibde.weatherapp.ui.fragment.LoadingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeLoadingFragment(): LoadingFragment
}