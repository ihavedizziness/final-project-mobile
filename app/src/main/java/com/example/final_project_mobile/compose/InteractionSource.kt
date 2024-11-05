package com.example.final_project_mobile.compose

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun defaultMutableInteractionSource() = remember {
    MutableInteractionSource()
}