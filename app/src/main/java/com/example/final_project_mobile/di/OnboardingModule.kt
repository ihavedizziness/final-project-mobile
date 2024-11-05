package com.example.final_project_mobile.di

import com.example.final_project_mobile.onboarding.OnboardingManager
import com.example.final_project_mobile.onboarding.presentation.OnboardingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val onboardingModule = module {
    single { OnboardingManager(context = androidApplication()) }

    viewModel { OnboardingViewModel(router = get()) }
}