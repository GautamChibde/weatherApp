package com.chibde.weatherapp.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class WeatherApiServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: WeatherApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(WeatherApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test not null`() {
        val weatherApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(WeatherApiService::class.java)
        assertThat(weatherApi, CoreMatchers.notNullValue())
    }

    @Test
    fun `test get weather api data`() = runBlocking {
        enqueueResponse("weather_data.json")

        val weatherData = service.getForecastAsync(
            days = 1,
            location = ""
        ).await()

        assertThat(weatherData.forecast, CoreMatchers.notNullValue())
        assertThat(weatherData.forecast!!.forecastDays.size, `is`(1))
        assertThat(weatherData.forecast!!.forecastDays.first().day!!.avgtemp, `is`(23.0))

        assertThat(weatherData.location, CoreMatchers.notNullValue())
        assertThat(weatherData.location!!.name, `is`("Bangalore"))

        assertThat(weatherData.current, CoreMatchers.notNullValue())
        assertThat(weatherData.current!!.tempC, `is`(21.0))
    }

    private fun enqueueResponse(fileName: String) {
        val inputStream = javaClass.classLoader
            .getResourceAsStream(fileName)
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}