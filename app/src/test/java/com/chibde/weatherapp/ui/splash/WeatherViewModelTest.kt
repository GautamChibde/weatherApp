package com.chibde.weatherapp.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.chibde.weatherapp.repository.WeatherDataRepository
import com.chibde.weatherapp.repository.WeatherDataResults
import com.chibde.weatherapp.ui.WeatherViewModel
import com.chibde.weatherapp.util.TestUtil
import com.chibde.weatherapp.util.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class WeatherViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val repository = Mockito.mock(WeatherDataRepository::class.java)
    private lateinit var viewModel: WeatherViewModel

    @Before
    fun init() {
        viewModel = WeatherViewModel(repository)
    }

    @Test
    fun testGetData() = runBlocking {
        val dataResults = WeatherDataResults(
            success = true,
            data = TestUtil.createDummyWeatherForecast()
        )
        whenever(repository.getWeatherData()).thenReturn(dataResults)
        val observer = mock<Observer<WeatherDataResults>>()
        viewModel.dataResults.observeForever(observer)
        withContext(Dispatchers.Default) {
            viewModel.getWeatherData()
            delay(100)
        }
        verify(observer).onChanged(dataResults)
    }

    @Test
    fun testErrorResponse() = runBlocking {
        val dataResults = WeatherDataResults(success = false, data = null)
        whenever(repository.getWeatherData()).thenReturn(dataResults)

        val observer = mock<Observer<WeatherDataResults>>()
        viewModel.dataResults.observeForever(observer)

        withContext(Dispatchers.Default) {
            viewModel.getWeatherData()
            delay(100)
        }

        verify(observer).onChanged(dataResults)
    }
}