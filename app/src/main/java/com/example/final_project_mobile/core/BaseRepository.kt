package com.example.final_project_mobile.core

interface BaseRepository {

    suspend fun <T> apiCall(result: suspend () -> T): Result<T> =
        runCatching { result.invoke() }.fold(::handleSuccess, ::handleError)

    suspend fun <FROM, TO> mappedApiCall(mapper: BaseMapper<FROM, TO>, call: suspend () -> FROM): Result<TO> =
        runCatching { call.invoke().let(mapper::map) }.fold(::handleSuccess, ::handleError)

    fun <R> handleSuccess(result: R): Result<R> = Result.success(result)

    fun <R> handleError(throwable: Throwable): Result<R> {
        throwable.printStackTrace()
        return Result.failure(throwable)
    }
}
