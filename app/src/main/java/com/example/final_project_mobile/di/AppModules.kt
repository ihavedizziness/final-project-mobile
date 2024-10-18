package com.example.final_project_mobile.di

import com.example.final_project_mobile.navigation.FlowNavigatorHolderQualifier
import com.example.final_project_mobile.navigation.FlowRouter
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import org.koin.dsl.module

val navigationModule = module {

    single<Cicerone<Router>> { Cicerone.create() }
    single<Router> { get<Cicerone<Router>>().router }
    single<NavigatorHolder> { get<Cicerone<Router>>().getNavigatorHolder() }

    single<FlowRouter> { FlowRouter(appRouter = get<Router>()) }

    single<NavigatorHolder>(FlowNavigatorHolderQualifier) { Cicerone.create(get<FlowRouter>()).getNavigatorHolder() }
}

val allModules = listOf(
    navigationModule,
    mainActivityModule,
)
