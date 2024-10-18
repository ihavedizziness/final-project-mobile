package com.example.final_project_mobile.core.android.initializer

import android.content.Context
import com.example.final_project_mobile.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin

class KoinInitializer : BaseInitializer<KoinApplication> {

    override fun create(context: Context) = startKoin {
        androidContext(context)
        modules(allModules)
    }
}
