package com.example.final_project_mobile.main

import androidx.annotation.DrawableRes
import com.example.final_project_mobile.R
import com.example.final_project_mobile.resources.string.AppString
import com.github.terrakok.cicerone.androidx.FragmentScreen

enum class BottomNavItem(
    val title: AppString,
    @DrawableRes val icon: Int,
    val screen: () -> FragmentScreen,
) {
    Home(AppString.BottomNavHome, R.drawable.icons_menu_home, { BottomNavScreens.HomeFlowScreen }),
    Search(AppString.BottomNavSearch, R.drawable.icons_menu_search, { BottomNavScreens.SearchFlowScreen }),
    Profile(AppString.BottomNavProfile, R.drawable.icons_menu_profile, { BottomNavScreens.ProfileFlowScreen });

    companion object {
        fun getTabByPosition(position: Int) = entries.find { it.ordinal == position } ?: Home
        fun valueOfOrNull(value: String) = BottomNavItem.entries.find { it.name == value }
    }
}
