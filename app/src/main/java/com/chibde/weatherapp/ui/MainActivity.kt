package com.chibde.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.chibde.weatherapp.R
import com.chibde.weatherapp.model.WeatherForecast
import com.chibde.weatherapp.ui.fragment.LoadingFragment
import com.chibde.weatherapp.ui.fragment.WeatherFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private fun getLocationPermission(): Array<String> {
        return arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
            loadFragment()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE ->
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadFragment()
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

    private fun loadFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, LoadingFragment())
            .commit()
    }

    fun weatherDataFragment(weatherForecast: WeatherForecast) {
        val fragment = WeatherFragment()
        val bundle = Bundle()
        bundle.putParcelable(WeatherFragment.DATA, weatherForecast)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 102
    }
}
