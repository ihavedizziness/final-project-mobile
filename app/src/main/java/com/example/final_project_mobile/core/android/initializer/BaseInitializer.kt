package com.example.final_project_mobile.core.android.initializer

import androidx.startup.Initializer

interface BaseInitializer<T> : Initializer<T> {
    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
