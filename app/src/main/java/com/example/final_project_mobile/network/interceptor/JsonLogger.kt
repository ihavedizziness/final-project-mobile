package com.example.final_project_mobile.network.interceptor

import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

class JsonLogger : HttpLoggingInterceptor.Logger {

    override fun log(message: String) {
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val json = JSONObject(message).toString(4)
                val jsonList = json.chunked(500)
                jsonList.forEach { Timber.tag("API").d(it) }
            } catch (e: JSONException) {
                Timber.tag("API_ERROR").d(e.toString())
            }
        } else {
            Timber.tag("API").d(message)
        }
    }
}