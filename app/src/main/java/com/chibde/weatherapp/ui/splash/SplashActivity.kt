package com.chibde.weatherapp.ui.splash

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chibde.weatherapp.R
import com.chibde.weatherapp.WeatherApp
import com.chibde.weatherapp.utils.AppViewModelFactory
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

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
        (application as WeatherApp).appComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SplashViewModel::class.java)

        viewModel.dataResults.observe(this, Observer {
            if (it.success) {
                Log.i("Data", "success ${it.data}")
            } else {
                Log.i("Data", "fail ${it.error}")
            }
        })
        requestPermissionsForApp()
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
            LOCATION_PERMISSION_REQUEST_CODE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                viewModel.getWeatherData()
            }
        }
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 102
    }
}
