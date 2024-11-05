package com.example.final_project_mobile.core

sealed class Resource<out T> {
    data object Initial : Resource<Nothing>()
    data class Loading(val count: Int) : Resource<Nothing>()
    data class Failure(val cause: Throwable) : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
}

fun <T> Resource<T>.toResult(): Result<T> {
    return kotlin.runCatching { this as Resource.Success<T> }.map { it.data }
}

fun <T> Result<T>.toResource(): Resource<T> {
    return fold({ Resource.Success(it) }, { Resource.Failure(it) })
}