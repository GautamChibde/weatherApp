package com.chibde.weatherapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chibde.weatherapp.R
import com.chibde.weatherapp.model.WeatherForecast
import com.chibde.weatherapp.testing.OpenForTesting
import com.chibde.weatherapp.ui.adapter.ForecastAdapter
import kotlinx.android.synthetic.main.fragment_weather.*

@OpenForTesting
class WeatherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val weatherForecast: WeatherForecast? = arguments?.getParcelable(DATA)
        weatherForecast?.let { weatherData ->
            tv_current_temp.text = String.format(
                getString(R.string.current_temperature),
                weatherData.current?.tempC ?: "N?A"
            )
            tv_location_name.text = weatherData.location?.name
            rv_forecast.layoutManager = LinearLayoutManager(context)
            weatherData.forecast?.forecastDays?.let {
                rv_forecast.adapter = ForecastAdapter(it)
            }
            val animation: Animation = AnimationUtils.loadAnimation(
                context,
                R.anim.bottom_top_animation
            )
            rv_forecast.animation = animation
            animation.start()
        }
    }

    companion object {
        const val DATA = "weather_data"
    }
}
