package com.example.final_project_mobile.ui.theme

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf

interface AbstractText

interface StringKey : Parcelable

data class NonTranslatableText(
    val text: String,
) : AbstractText

data class TranslatableText(
    val ru: String,
    val en: String,
    val kk: String,
) : AbstractText

@Stable
interface TextProvider {

    val strings: Map<StringKey, AbstractText>
    val localeState: LocaleState

    fun getString(key: StringKey): String {
        return this.getLocaleString(key)
    }

    fun getLocaleString(id: StringKey): String {
        return when (val text = strings[id]) {
            is NonTranslatableText -> {
                text.text
            }
            is TranslatableText -> {
                when (localeState.locale) {
                    Locales.EN.localeTag -> text.en
                    Locales.KK.localeTag -> text.kk
                    else -> text.ru
                }
            }
            else -> { "" }
        }
    }
}

val LocalTextProvider = compositionLocalOf<TextProvider> {
    DefaultTextProvider()
}

class DefaultTextProvider(
    override val strings: Map<StringKey, AbstractText> = emptyMap(),
    override val localeState: LocaleState = LocaleState(locale = "en"),
) : TextProvider

@Composable
@ReadOnlyComposable
fun stringRes(key: StringKey): String {
    return LocalTextProvider.current.getLocaleString(key)
}
