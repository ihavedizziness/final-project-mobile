package com.example.final_project_mobile.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.final_project_mobile.navigation.FlowItemFragment
import com.example.final_project_mobile.navigation.ScreenArgs
import com.example.final_project_mobile.navigation.getFragmentArgs
import com.example.final_project_mobile.ui.createComposeViewWithAppTheme
import com.example.final_project_mobile.ui.theme.AppTheme
import com.example.final_project_mobile.ui.theme.Graphik
import kotlinx.parcelize.Parcelize

class FilmDetailFragment : FlowItemFragment() {

    @Parcelize
    data class Args(
        val filmId: Long,
    ) : ScreenArgs()

    private val args: Args by getFragmentArgs<Args>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeViewWithAppTheme {
        FilmDetailsScreen()
    }

    @Composable
    fun FilmDetailsScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.white),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = args.filmId.toString(),
                color = AppTheme.colors.contrast,
                fontFamily = FontFamily.Graphik,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
            )
        }
    }
}