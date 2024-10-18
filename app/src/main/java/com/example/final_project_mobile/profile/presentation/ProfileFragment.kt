package com.example.final_project_mobile.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.example.final_project_mobile.navigation.FlowItemFragment
import com.example.final_project_mobile.ui.createComposeViewWithAppTheme

class ProfileFragment : FlowItemFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = createComposeViewWithAppTheme { ProfileContent() }

    @Composable
    fun ProfileContent() {
        Text(
            text = "Profile",
            fontSize = 64.sp,
        )
    }
}
