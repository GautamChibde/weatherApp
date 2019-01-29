package com.chibde.weatherapp.ui.main

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chibde.weatherapp.R
import com.chibde.weatherapp.model.WeatherForecast
import com.chibde.weatherapp.utils.getParcel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherForecast: WeatherForecast? = intent.getParcel("data")
        weatherForecast?.let { weatherData ->
            tv_current_temp.text = String.format(
                getString(R.string.current_temperature),
                weatherData.current?.tempC ?: "N?A"
            )
            tv_location_name.text = weatherData.location?.name
            rv_forecast.layoutManager = LinearLayoutManager(this)
            weatherData.forecast?.forecastDays?.let {
                rv_forecast.adapter = ForecastAdapter(it)
            }
            val animation: Animation = AnimationUtils.loadAnimation(
                this,
                R.anim.bottom_top_animation
            )
            rv_forecast.animation = animation
            animation.start()
        }
    }
}
