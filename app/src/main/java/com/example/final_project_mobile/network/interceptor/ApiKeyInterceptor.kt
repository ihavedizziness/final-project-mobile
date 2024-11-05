package com.example.final_project_mobile.network.interceptor

import com.example.final_project_mobile.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("X-API-KEY", BuildConfig.KINOPOISK_API_KEY)
                .method(chain.request().method, chain.request().body)
                .build()
        )
    }
}