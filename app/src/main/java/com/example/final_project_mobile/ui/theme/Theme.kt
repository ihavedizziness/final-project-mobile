package com.example.final_project_mobile.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun AppTheme(
    colors: AppColors = AppTheme.colors,
    typography: Typography = AppTheme.typography,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalTypography provides typography
    ) {
        content()
    }
}

object AppTheme {
    val colors: AppColors
        get() = AppLightColors()

    val typography: Typography
        get() = Typography(
            title = TextStyle(
                color = colors.black,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                fontStyle = FontStyle.Normal,
            )
        )
}

@Composable
fun AppStrings(
    textProvider: TextProvider,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalTextProvider provides textProvider
    ) {
        content()
    }
}
