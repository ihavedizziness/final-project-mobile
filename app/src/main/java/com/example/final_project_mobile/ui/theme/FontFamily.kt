package com.example.final_project_mobile.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.final_project_mobile.R

val FontFamily.Companion.Graphik: FontFamily
    get() = FontFamily(
        Font(
            resId = R.font.graphik_regular,
            weight = FontWeight.Normal,
        ),
        Font(
            resId = R.font.graphik_medium,
            weight = FontWeight.Medium,
        ),
        Font(
            resId = R.font.graphik_semibold,
            weight = FontWeight.SemiBold
        ),
        Font(
            resId = R.font.graphik_bold,
            weight = FontWeight.Bold,
        )
    )