package com.example.final_project_mobile.ui.theme

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalColors: ProvidableCompositionLocal<AppColors> = staticCompositionLocalOf { AppUnspecifiedColors }

interface AppColors {
    val white: Color
    val black: Color
    val accent: Color
    val contrast: Color
    val imagePlaceholder: Color
    val hint: Color
}

data class AppLightColors(
    override val white: Color = Color(0xFFFFFFFF),
    override val black: Color = Color(0xFF000000),
    override val accent: Color = Color(0xFF3D3BFF),
    override val contrast: Color = Color(0xFF272727),
    override val imagePlaceholder: Color = Color(0x66B5B5C9),
    override val hint: Color = Color(0xFF838390)
) : AppColors

object AppUnspecifiedColors : AppColors {
    override val white: Color = Color.Unspecified
    override val black: Color = Color.Unspecified
    override val accent: Color = Color.Unspecified
    override val contrast: Color = Color.Unspecified
    override val imagePlaceholder: Color = Color.Unspecified
    override val hint: Color = Color.Unspecified
}
