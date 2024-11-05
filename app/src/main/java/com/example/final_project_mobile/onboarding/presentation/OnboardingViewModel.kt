package com.example.final_project_mobile.onboarding.presentation

import androidx.lifecycle.ViewModel
import com.example.final_project_mobile.core.Action
import com.example.final_project_mobile.navigation.FlowRouter

class OnboardingViewModel(
    private val router: FlowRouter,
) : ViewModel() {

    fun dispatch(action: Action) = when (action) {
        is OnboardingAction.OnSkipClicked -> finishOnboarding()
        else -> Unit
    }

    private fun finishOnboarding() {
        router.finishFlow()
        router.appRouter.sendResult(ONBOARDING_FINISH_KEY, Unit)
    }

    sealed interface OnboardingAction : Action {
        data object OnSkipClicked : OnboardingAction
    }

    companion object {
        const val ONBOARDING_FINISH_KEY = "onboarding_finish_key"
    }
}