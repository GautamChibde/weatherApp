package com.chibde.weatherapp.utils

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.chibde.weatherapp.TestApp

class AndroidTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}