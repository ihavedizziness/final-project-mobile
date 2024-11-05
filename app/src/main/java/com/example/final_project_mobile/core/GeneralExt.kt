package com.example.final_project_mobile.core

fun Any?.isNull() = (this == null)

fun Double?.orZero(): Double = this ?: 0.0
fun Float?.orZero(): Float = this ?: 0f
fun Int?.orZero(): Int = this ?: 0
fun Long?.orZero(): Long = this ?: 0L
fun Boolean?.orFalse() = (this ?: false)