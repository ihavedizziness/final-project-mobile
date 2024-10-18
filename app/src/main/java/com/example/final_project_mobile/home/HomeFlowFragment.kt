package com.example.final_project_mobile.home

import com.example.final_project_mobile.main.BottomNavScreens
import com.example.final_project_mobile.navigation.FlowContainerFragment
import com.example.final_project_mobile.navigation.FragmentTransactionAnimation
import com.github.terrakok.cicerone.Screen

class HomeFlowFragment :
    FragmentTransactionAnimation.Fade,
    FlowContainerFragment() {

    override val startDestination: Screen = BottomNavScreens.HomeScreen
    override val isShowingBottomNavigation: Boolean = true
    override val isRootFlowController: Boolean = true
}
