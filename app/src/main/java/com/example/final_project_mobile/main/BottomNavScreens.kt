package com.example.final_project_mobile.main

import com.example.final_project_mobile.home.HomeFlowFragment
import com.example.final_project_mobile.home.presentation.HomeFragment
import com.example.final_project_mobile.navigation.bottomNavScreen
import com.example.final_project_mobile.navigation.fragmentScreen
import com.example.final_project_mobile.onboarding.presentation.OnBoardingFragment
import com.example.final_project_mobile.profile.ProfileFlowFragment
import com.example.final_project_mobile.profile.presentation.ProfileFragment
import com.example.final_project_mobile.search.SearchFlowFragment
import com.example.final_project_mobile.search.presentation.SearchFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object BottomNavScreens {

    // nav containers / flow
    object HomeFlowScreen : FragmentScreen by bottomNavScreen<HomeFlowFragment>(BottomNavItem.Home)
    object SearchFlowScreen : FragmentScreen by bottomNavScreen<SearchFlowFragment>(BottomNavItem.Search)
    object ProfileFlowScreen : FragmentScreen by bottomNavScreen<ProfileFlowFragment>(BottomNavItem.Profile)

    // flows start destinations
    object HomeScreen: FragmentScreen by fragmentScreen<HomeFragment>()
    object SearchScreen: FragmentScreen by fragmentScreen<SearchFragment>()
    object ProfileScreen: FragmentScreen by fragmentScreen<ProfileFragment>()

    object OnboardingScreen: FragmentScreen by fragmentScreen<OnBoardingFragment>()
}
