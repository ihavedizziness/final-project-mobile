package com.example.final_project_mobile.resources.string

import com.example.final_project_mobile.core.emptyString
import com.example.final_project_mobile.ui.theme.AbstractText
import com.example.final_project_mobile.ui.theme.NonTranslatableText
import com.example.final_project_mobile.ui.theme.StringKey
import com.example.final_project_mobile.ui.theme.TranslatableText

val appStrings: Map<StringKey, AbstractText> = mapOf(
    AppString.EmptyString to NonTranslatableText(emptyString()),
    AppString.BottomNavHome to TranslatableText(
        ru = "Главная",
        en = "Home",
        kk = "Басты бет",
    ),
    AppString.BottomNavSearch to TranslatableText(
        ru = "Поиск",
        en = "Search",
        kk = "Іздеу",
    ),
    AppString.BottomNavProfile to TranslatableText(
        ru = "Профиль",
        en = "Profile",
        kk = "Профиль",
    ),
)
