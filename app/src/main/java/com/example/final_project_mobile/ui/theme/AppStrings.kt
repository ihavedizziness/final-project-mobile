package com.example.final_project_mobile.ui.theme

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object AppStrings {

    private const val LOCALE_KEY = "locale.key"
    private const val LOCALE_DATABASE_KEY = "locale.database.key"

    private var settingsStore: DataStore<Preferences>? = null
    private val localeKey = stringPreferencesKey(LOCALE_KEY)
    private val initialLocale get() = getInitialLanguageWithMigration()
    private val _localeFlow = MutableStateFlow(LocaleState(locale = initialLocale))
    val localeFlow = _localeFlow.asStateFlow()

    fun initLanguageStore(context: Context) {
        settingsStore = PreferenceDataStoreFactory.create {
            context.applicationContext.preferencesDataStoreFile(LOCALE_DATABASE_KEY)
        }
        _localeFlow.value = LocaleState(locale = initialLocale)
    }

    private fun getInitialLanguageWithMigration(): String = runBlocking {
        return@runBlocking settingsStore?.data?.first()?.get(localeKey).orEmpty().ifBlank {
            AppCompatDelegate.getApplicationLocales()
                .toLanguageTags().takeIf(String::isNotBlank) ?: Locales.EN.localeTag
        }
    }
}

data class LocaleState(val locale: String)

enum class Locales(
    val localeTag: String,
    val localeName: String,
) {
    EN(localeTag = "en", localeName = "English"),
    RU(localeTag = "ru", localeName = "Русский"),
    KK(localeTag = "kk", localeName = "Қазақша"),
}
