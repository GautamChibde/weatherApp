package com.chibde.weatherapp.ui.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.chibde.weatherapp.R
import com.chibde.weatherapp.WeatherApp
import com.chibde.weatherapp.ui.main.MainActivity
import com.chibde.weatherapp.utils.AppViewModelFactory
import kotlinx.android.synthetic.main.activity_splash.*
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
                it.data?.let {
                    startMainActivity()
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

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
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

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 102
    }
}