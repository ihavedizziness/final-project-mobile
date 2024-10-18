package com.example.final_project_mobile.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.final_project_mobile.navigation.FlowItemFragment
import com.example.final_project_mobile.ui.createComposeViewWithAppTheme

class SearchFragment : FlowItemFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeViewWithAppTheme { SearchContent() }

    @Composable
    fun SearchContent() {
        Text(
            text = "Search",
            fontSize = 64.sp,
        )
    }
}
