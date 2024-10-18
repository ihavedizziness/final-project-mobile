package com.example.final_project_mobile.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen

class FlowRouter(private val appRouter: Router) : Router() {

    fun startFlow(screen: Screen) = appRouter.navigateTo(screen)

    fun newRootFlow(screen: Screen) = appRouter.newRootScreen(screen)

    fun finishFlow() = appRouter.exit()
}
