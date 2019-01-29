package com.chibde.weatherapp.ui.splash

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.chibde.weatherapp.R
import com.chibde.weatherapp.di.Injectable
import com.chibde.weatherapp.model.WeatherForecast
import com.chibde.weatherapp.ui.main.MainActivity
import com.chibde.weatherapp.utils.AppViewModelFactory
import com.chibde.weatherapp.utils.putParcel
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), HasActivityInjector, Injectable {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SplashViewModel

    private fun getLocationPermission(): Array<String> {
        return arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SplashViewModel::class.java)

        viewModel.dataResults.observe(this, Observer {
            if (it.success) {
                it.data?.let {
                    startMainActivity(it)
                } ?: run {
                    showError()
                }
            } else {
                showError()
            }
        })
        requestPermissionsForApp()
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
                this,
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
                    this,
                    R.color.background
                )
            )
        }
    }

    private fun startMainActivity(weatherForecast: WeatherForecast) {
        val intent = Intent(this, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putParcel("data", weatherForecast)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun requestPermissionsForApp() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, getLocationPermission(),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            viewModel.getWeatherData()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE ->
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewModel.getWeatherData()
                } else {
                    MaterialDialog(this)
                        .cancelOnTouchOutside(false)
                        .message(R.string.error_dialog_title)
                        .negativeButton(R.string.exit) {
                            finish()
                        }
                        .positiveButton(R.string.grant) {
                            requestPermissionsForApp()
                            it.dismiss()
                        }.show()
                }
        }
    }

    override fun activityInjector() = dispatchingAndroidInjector

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 102
    }
}
