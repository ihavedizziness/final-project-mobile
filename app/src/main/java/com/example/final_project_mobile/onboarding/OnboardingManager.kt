package com.example.final_project_mobile.onboarding

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class OnboardingManager(context: Context) {

    private val dataStore = context.dataStore
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun shouldShowOnBoarding(): Boolean = runBlocking {
       dataStore.data.first()[FIRST_LAUNCH] ?: true
    }

    fun onBoardingShown() = coroutineScope.launch {
        dataStore.edit { prefs ->
            prefs[FIRST_LAUNCH] = false
        }
    }

    companion object {
        private val FIRST_LAUNCH = booleanPreferencesKey("first_launch")

        private val Context.dataStore by preferencesDataStore("onboardingDataStore")
    }
}