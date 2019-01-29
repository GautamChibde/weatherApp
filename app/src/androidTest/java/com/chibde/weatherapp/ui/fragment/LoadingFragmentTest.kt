package com.chibde.weatherapp.ui.fragment

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.chibde.weatherapp.R
import com.chibde.weatherapp.repository.WeatherDataResults
import com.chibde.weatherapp.testing.SingleFragmentActivity
import com.chibde.weatherapp.ui.WeatherViewModel
import com.chibde.weatherapp.util.TestUtil
import com.chibde.weatherapp.utils.ViewModelUtil
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class LoadingFragmentTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, true, true)
    private lateinit var viewModel: WeatherViewModel

    private val dataResults = MutableLiveData<WeatherDataResults>()

    private val loadingFragment = TestLoadingFragment()

    @Before
    fun init() {
        viewModel = mock(WeatherViewModel::class.java)
        `when`(viewModel.dataResults).thenReturn(dataResults)
        loadingFragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        activityRule.activity.setFragment(loadingFragment)
    }

    @Test
    fun testLoadingProgress() {
        onView(withId(R.id.iv_progress)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_error)).check(matches(not(isDisplayed())))
        onView(withId(R.id.btn_retry)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testErrorPage() {
        val data = WeatherDataResults(
            success = false,
            data = null
        )
        dataResults.postValue(data)
        onView(withId(R.id.iv_progress)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tv_error)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_retry)).check(matches(isDisplayed()))
    }

    @Test
    fun testErrorPageWhenDataNull() {
        val data = WeatherDataResults(
            success = true,
            data = null
        )
        dataResults.postValue(data)
        onView(withId(R.id.iv_progress)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tv_error)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_retry)).check(matches(isDisplayed()))
    }

    @Test
    fun testSuccess() {
        val data = WeatherDataResults(
            success = true,
            data = TestUtil.createDummyWeatherForecast()
        )
        dataResults.postValue(data)
        onView(withId(R.id.weather_fragment_container)).check(matches(isDisplayed()))
    }

    class TestLoadingFragment : LoadingFragment()
}