package com.example.final_project_mobile.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen

class FlowRouter(val appRouter: Router) : Router() {

    fun startFlowWithAdd(screen: Screen) = appRouter.navigateTo(screen)

    fun startFlowWithReplace(screen: Screen) = appRouter.replaceScreen(screen)

    fun newRootFlow(screen: Screen) = appRouter.newRootScreen(screen)

    fun finishFlow() = appRouter.exit()
}
