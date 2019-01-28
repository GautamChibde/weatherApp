package com.chibde.weatherapp.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.chibde.weatherapp.repository.WeatherDataRepository
import com.chibde.weatherapp.repository.WeatherDataResults
import com.chibde.weatherapp.util.TestUtil
import com.chibde.weatherapp.util.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class SplashViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = Mockito.mock(WeatherDataRepository::class.java)
    private lateinit var viewModel: SplashViewModel
    private val weatherDataResults = MutableLiveData<WeatherDataResults>()

    @Before
    fun init() {
        Mockito.`when`(repository.weatherDataResults).thenReturn(weatherDataResults)
        viewModel = SplashViewModel(repository)
    }

    @Test
    fun testGetData() {
        val dataResults = WeatherDataResults(
            success = true,
            data = TestUtil.createDummyWeatherForecast()
        )
        Mockito.`when`(repository.getWeatherData()).then {
            weatherDataResults.postValue(dataResults)
        }
        val observer = mock<Observer<WeatherDataResults>>()
        viewModel.dataResults.observeForever(observer)
        viewModel.getWeatherData()
        verify(observer).onChanged(dataResults)

        val dataResults2 = WeatherDataResults(success = false, data = null)
        Mockito.`when`(repository.getWeatherData()).then {
            weatherDataResults.postValue(dataResults2)
        }
        viewModel.getWeatherData()
        verify(observer).onChanged(dataResults2)
    }
}