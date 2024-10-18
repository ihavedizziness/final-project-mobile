package com.example.final_project_mobile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.example.final_project_mobile.resources.string.appStrings
import com.example.final_project_mobile.ui.theme.AppStrings
import com.example.final_project_mobile.ui.theme.AppTheme
import com.example.final_project_mobile.ui.theme.DefaultTextProvider

fun Fragment.createComposeViewWithAppTheme(
    content: @Composable () -> Unit,
): ComposeView = ComposeView(requireContext()).apply {
    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner))
    setContentWithTheme(content)
}

fun ComposeView.setContentWithTheme(content: @Composable () -> Unit) {
    setContent {
        AppTheme {
            val localeState by AppStrings.localeFlow.collectAsState()
            AppStrings(textProvider = DefaultTextProvider(appStrings, localeState), content)
        }
    }
}