package com.chibde.weatherapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.chibde.weatherapp.api.WeatherApiService
import com.chibde.weatherapp.util.TestUtil
import com.chibde.weatherapp.utils.LocationManager
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class WeatherDataRepositoryTest {
    private lateinit var repository: WeatherDataRepository
    private val service = Mockito.mock(WeatherApiService::class.java)
    private val locationManager = Mockito.mock(LocationManager::class.java)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        repository = WeatherDataRepository(service, locationManager)
    }

    @Test
    fun testGetWeatherData() = runBlocking {
        whenever(locationManager.getFormatterLocationString()).thenReturn("12.3,14.4")
        val weatherDataResponse = TestUtil.createDummyWeatherForecast()

        Mockito.`when`(
            service.getForecastAsync(
                days = 4,
                location = "12.3,14.4"
            )
        ).doAnswer {
            GlobalScope.async {
                weatherDataResponse
            }
        }
        val weatherDataResults = async { repository.getWeatherData() }.await()
        assertThat(
            weatherDataResults, `is`(
                WeatherDataResults(
                    success = true,
                    error = "",
                    data = weatherDataResponse
                )
            )
        )
    }
}