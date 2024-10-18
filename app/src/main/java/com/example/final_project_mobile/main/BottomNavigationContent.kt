package com.example.final_project_mobile.main

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.final_project_mobile.ui.theme.AppTheme
import com.example.final_project_mobile.ui.theme.stringRes
import kotlinx.coroutines.launch

@Composable
fun BottomNavigationContent(
    bottomNavigationState: BottomNavState,
    onNavigationItemSelected: (BottomNavItem) -> Unit,
) {
    val bottomNavItemEnumEntries = BottomNavItem.entries
    val coroutineScope = rememberCoroutineScope()

    NavigationBar(
        containerColor = AppTheme.colors.white,
    ) {
        bottomNavItemEnumEntries.forEach { item ->
            val scale = remember { Animatable(1f) }

            NavigationBarItem(
                modifier = Modifier
                    .scale(scale.value)
                    .padding(top = 4.dp),
                colors = getAppNavBarColors(),
                selected = bottomNavigationState.currentNavItem == item,
                icon = { DefaultMenuItem(item) },
                onClick = {
                    coroutineScope.launch {
                        scale.animateTo(
                            targetValue = 0.7f,
                            animationSpec = tween(
                                durationMillis = 150,
                                easing = FastOutSlowInEasing,
                            )
                        )
                        scale.animateTo(
                            targetValue = 1f,
                            animationSpec = tween(
                                durationMillis = 150,
                                easing = FastOutSlowInEasing,
                            )
                        )
                        onNavigationItemSelected(item)
                    }
                }
            )
        }
    }
}

@Composable
private fun DefaultMenuItem(item: BottomNavItem) {
    Icon(
        painter = painterResource(item.icon),
        contentDescription = stringRes(item.title),
    )
}


data class BottomNavState(val currentNavItem: BottomNavItem)

fun getAppNavBarColors() = NavigationBarItemColors(
    selectedIconColor = AppTheme.colors.accent,
    unselectedIconColor = AppTheme.colors.contrast,
    selectedIndicatorColor = Color.Unspecified,
    selectedTextColor = Color.Unspecified,
    unselectedTextColor = Color.Unspecified,
    disabledIconColor = Color.Unspecified,
    disabledTextColor = Color.Unspecified,
)
