package com.chibde.weatherapp.api

import com.chibde.weatherapp.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .url(
                    chain.request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("key", Constants.API_KEY)
                        .build()
                )
                .build()
        )
    }
}