package com.chibde.weatherapp.ui.fragment

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.chibde.weatherapp.R
import com.chibde.weatherapp.testing.SingleFragmentActivity
import com.chibde.weatherapp.util.TestUtil
import com.chibde.weatherapp.utils.RecyclerViewItemCountAssertion
import com.chibde.weatherapp.utils.RecyclerViewMatcher
import com.chibde.weatherapp.utils.getDayString
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherFragmentTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, true, true)

    private val weatherData = TestUtil.createDummyWeatherForecast()

    private val weatherFragment = TestWeatherFragment().apply {
        arguments = Bundle().apply {
            putParcelable(WeatherFragment.DATA, weatherData)
        }
    }

    @Before
    fun init() {
        activityRule.activity.setFragment(weatherFragment)
    }

    @Test
    fun testDataLoaded() {

        val currentTemp = "${weatherData.current!!.tempC}\u00B0"

        val recyclerViewTemp = "${weatherData
            .forecast!!
            .forecastDays
            .first()
            .day!!
            .avgtemp} C"
        val date = weatherData.forecast!!
            .forecastDays
            .first()
            .date!!
            .getDayString()

        onView(withId(R.id.tv_current_temp)).check(matches(withText(currentTemp)))
        onView(withId(R.id.tv_location_name)).check(matches(withText(weatherData.location?.name!!)))

        onView((withId(R.id.rv_forecast))).check(
            RecyclerViewItemCountAssertion(weatherData.forecast!!.forecastDays.size)
        )

        onView(RecyclerViewMatcher(R.id.rv_forecast).atPosition(0))
            .check(matches(hasDescendant(withText(recyclerViewTemp))))
        onView(RecyclerViewMatcher(R.id.rv_forecast).atPosition(0))
            .check(matches(hasDescendant(withText(date))))
    }

    class TestWeatherFragment : WeatherFragment()
}