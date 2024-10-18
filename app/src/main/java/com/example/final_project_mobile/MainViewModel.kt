package com.example.final_project_mobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project_mobile.main.BottomNavItem
import com.example.final_project_mobile.main.BottomNavState
import com.example.final_project_mobile.navigation.FlowRouter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    val router: FlowRouter,
) : ViewModel() {

    private val _currentBottomNavItem = MutableStateFlow(BottomNavItem.Home)
    val currentBottomNavItem = _currentBottomNavItem.asStateFlow()

    val bottomNavigationState = _currentBottomNavItem
        .map { currentTabItem ->
            BottomNavState(currentTabItem)
        }.stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            BottomNavState(BottomNavItem.Home)
        )

    fun onBottomNavItemClicked(bottomNavItem: BottomNavItem) {
        _currentBottomNavItem.value = bottomNavItem
    }
}
