package com.example.final_project_mobile

import com.example.final_project_mobile.main.BottomNavItem

interface ActivityEnvRouter {
    fun goToTab(tab: BottomNavItem)
    fun getCurrentTab(): BottomNavItem
}
