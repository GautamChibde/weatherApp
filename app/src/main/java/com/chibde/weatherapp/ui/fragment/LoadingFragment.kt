package com.chibde.weatherapp.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.chibde.weatherapp.R
import com.chibde.weatherapp.di.Injectable
import com.chibde.weatherapp.ui.MainActivity
import com.chibde.weatherapp.ui.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_loading.*
import javax.inject.Inject


class LoadingFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(WeatherViewModel::class.java)

        viewModel.dataResults.observe(this, Observer { weatherDataResults ->
            if (weatherDataResults.success) {
                weatherDataResults.data?.let { weatherForecast ->
                    (activity as MainActivity).weatherDataFragment(weatherForecast)
                } ?: run {
                    showError()
                }
            } else {
                showError()
            }
        })
        viewModel.getWeatherData()
        initViews()
    }

    private fun initViews() {
        Glide.with(this)
            .load(R.drawable.progress_loading)
            .into(iv_progress)
    }

    private fun showError() {
        container.setBackgroundColor(
            ContextCompat.getColor(
                context!!,
                R.color.background_error
            )
        )
        btn_retry.visibility = View.VISIBLE
        tv_error.visibility = View.VISIBLE
        iv_progress.visibility = View.INVISIBLE
        btn_retry.setOnClickListener {
            viewModel.getWeatherData()
            btn_retry.visibility = View.INVISIBLE
            tv_error.visibility = View.INVISIBLE
            iv_progress.visibility = View.VISIBLE
            container.setBackgroundColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.background
                )
            )
        }
    }
}
