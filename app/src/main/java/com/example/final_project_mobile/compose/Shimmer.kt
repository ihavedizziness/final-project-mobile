package com.example.final_project_mobile.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.final_project_mobile.compose.loading.PlaceholderHighlight
import com.example.final_project_mobile.compose.loading.placeholder
import com.example.final_project_mobile.compose.loading.shimmer
import com.example.final_project_mobile.ui.theme.AppTheme

@Composable
fun Shimmer(
    modifier: Modifier = Modifier,
    radius: Dp = 10.dp,
    color: Color = AppTheme.colors.grey20,
) {
    Box(modifier.shimmer(radius, color))
}

@[Composable Suppress("MutableStateAutoboxing")]
fun Modifier.shimmer(
    radius: Dp,
    color: Color = AppTheme.colors.grey20,
) = this.placeholder(
    visible = true,
    color = color,
    highlight = PlaceholderHighlight.shimmer(Color.White),
    shape = RoundedCornerShape(radius),
)