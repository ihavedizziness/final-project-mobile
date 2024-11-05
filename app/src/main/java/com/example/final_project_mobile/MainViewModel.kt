package com.example.final_project_mobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project_mobile.core.Action
import com.example.final_project_mobile.main.BottomNavItem
import com.example.final_project_mobile.main.BottomNavState
import com.example.final_project_mobile.navigation.FlowRouter
import com.example.final_project_mobile.navigation.setResultListener
import com.example.final_project_mobile.onboarding.OnboardingManager
import com.example.final_project_mobile.onboarding.OnboardingScreens
import com.example.final_project_mobile.onboarding.presentation.OnboardingViewModel.Companion.ONBOARDING_FINISH_KEY
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    val router: FlowRouter,
    private val onboardingManager: OnboardingManager,
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

    init {
        router.appRouter.setResultListener<Unit>(ONBOARDING_FINISH_KEY) {
            viewModelScope.launch {
                delay(500)
                onboardingManager.onBoardingShown()
            }
        }
    }

    fun onBottomNavItemClicked(bottomNavItem: BottomNavItem) {
        _currentBottomNavItem.value = bottomNavItem
    }

    fun dispatch(action: Action) = when (action) {
        is Action.ShowOnboarding -> router.startFlowWithAdd(OnboardingScreens.OnboardingFlowScreen)
    }

    sealed interface Action {
        data object ShowOnboarding : Action
    }
}
