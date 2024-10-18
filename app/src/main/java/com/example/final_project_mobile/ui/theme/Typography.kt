package com.example.final_project_mobile.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

val LocalTypography = staticCompositionLocalOf { Typography() }

data class Typography(
    val title: TextStyle = TextStyle(),
)