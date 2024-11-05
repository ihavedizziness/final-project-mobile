package com.example.final_project_mobile.di

import android.content.Context
import com.example.final_project_mobile.BuildConfig
import com.example.final_project_mobile.network.FilmService
import com.example.final_project_mobile.network.interceptor.ApiKeyInterceptor
import com.example.final_project_mobile.network.interceptor.JsonLogger
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    factory {
        HttpLoggingInterceptor(
            logger = JsonLogger()
        ).apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    factory { ApiKeyInterceptor() }

    factory {
        provideOkHttpClient(
            context = androidContext(),
            apiKeyInterceptor = get(),
            loggingInterceptor = get(),
        )
    }

    single {
        provideMoshi()
    }

    single {
        provideRetrofit(
            moshi = get(),
            okHttpClient = get(),
        )
    }
}

val servicesModule = module {
    factory { createService<FilmService>(retrofit = get()) }
}

private const val TIMEOUT = 60L
private const val CACHE_SIZE = 10L * 1024L * 1024L

private fun provideOkHttpClient(
    context: Context,
    apiKeyInterceptor: ApiKeyInterceptor,
    loggingInterceptor: HttpLoggingInterceptor,
) : OkHttpClient = OkHttpClient().newBuilder()
    .cache(Cache(context.cacheDir, CACHE_SIZE))
    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
    .addInterceptor(apiKeyInterceptor)
    .addInterceptor(loggingInterceptor)
    .cache(Cache(context.cacheDir, CACHE_SIZE))
    .build()

private fun provideMoshi() = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun provideRetrofit(
    moshi: Moshi,
    okHttpClient: OkHttpClient,
) : Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
    .build()

inline fun <reified T> createService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}