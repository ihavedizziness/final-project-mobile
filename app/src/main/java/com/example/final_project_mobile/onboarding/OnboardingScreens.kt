package com.example.final_project_mobile.onboarding

import com.example.final_project_mobile.navigation.fragmentScreen
import com.example.final_project_mobile.onboarding.presentation.OnboardingFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object OnboardingScreens {

    object OnboardingFlowScreen : FragmentScreen by fragmentScreen<OnboardingFlowFragment>(clearContainer = true)

    object OnBoardingScreen : FragmentScreen by fragmentScreen<OnboardingFragment>(clearContainer = true)
}