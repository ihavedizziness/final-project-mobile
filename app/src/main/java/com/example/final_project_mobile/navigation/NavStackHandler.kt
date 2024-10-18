package com.example.final_project_mobile.navigation

import java.util.Stack

data class NavStackItem(
    val isRootFlowController: Boolean,
    val isShowBottomNavigation: Boolean,
)

interface NavStackHandler {
    val navControllerStack: Stack<NavStackItem>
    val currentNavigationItem: NavStackItem?
        get() {
            return if (navControllerStack.isNotEmpty()) {
                navControllerStack.peek()
            } else { null }
        }

    fun pushNavigationController(item: NavStackItem)
    fun popNavigationController()
}

interface NavigationFlowFragment {
    var navStackHandler: NavStackHandler?
}
