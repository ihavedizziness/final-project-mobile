package com.example.final_project_mobile.onboarding

import com.example.final_project_mobile.main.BottomNavScreens
import com.example.final_project_mobile.navigation.FlowContainerFragment
import com.example.final_project_mobile.navigation.FragmentTransactionAnimation
import com.github.terrakok.cicerone.Screen

class OnboardingFlowFragment :
    FragmentTransactionAnimation.Fade,
    FlowContainerFragment() {

    override val startDestination: Screen = BottomNavScreens.OnboardingScreen
    override val isShowingBottomNavigation: Boolean = false
    override val isRootFlowController: Boolean = true
}