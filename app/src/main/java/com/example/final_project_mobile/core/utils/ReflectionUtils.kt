package com.example.final_project_mobile.core.utils

inline fun <reified T> className(): String = T::class.java.name
