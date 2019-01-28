package com.chibde.weatherapp.api

import android.content.Context
import android.net.ConnectivityManager
import com.chibde.weatherapp.api.exceptions.BadRequestException
import com.chibde.weatherapp.api.exceptions.InternalServerException
import com.chibde.weatherapp.api.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(
    context: Context
) : Interceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoConnectivityException()
        val response = chain.proceed(chain.request())

        if (response.code() in 400..499)
            throw BadRequestException("Failed to complete request, Possible Reason: ${response.body()}")

        if (response.code() >= 500)
            throw InternalServerException("Failed to complete request, Possible Reason: ${response.body()}")

        return response
    }


    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}