package com.chibde.weatherapp.util

import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.chibde.weatherapp.model.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object TestUtil {

    fun createDummyWeatherForecast(): WeatherForecast {
        return WeatherForecast(
            Current(tempC = 23.2),
            Forecast(
                listOf(
                    ForecastDay(
                        date = "2019-01-28",
                        day = Day(
                            avgtemp = 23.4
                        )
                    ), ForecastDay(
                        date = "2019-01-29",
                        day = Day(
                            avgtemp = 23.1
                        )
                    )
                )
            ),
            Location(
                lat = 12.3,
                lon = 13.4,
                name = "London"
            )
        )
    }
}

@Throws(InterruptedException::class)
fun <T> LiveData<T>.getValueSync(): T {
    val data = arrayOfNulls<Any>(1)
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(@Nullable o: T) {
            data[0] = o
            latch.countDown()
            removeObserver(this)
        }
    }
    this.observeForever(observer)
    latch.await(2, TimeUnit.SECONDS)

    return data[0] as T
}