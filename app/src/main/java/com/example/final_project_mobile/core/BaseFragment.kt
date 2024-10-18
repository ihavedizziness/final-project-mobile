package com.example.final_project_mobile.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.final_project_mobile.navigation.FlowNavigatorHolderQualifier
import com.example.final_project_mobile.navigation.FlowRouter
import com.github.terrakok.cicerone.NavigatorHolder
import org.koin.android.ext.android.inject

abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    open val router by inject<FlowRouter>()
    open val navigationHolder by inject<NavigatorHolder>(FlowNavigatorHolderQualifier)

    open fun onBackPressed() {
        router.exit()
    }
}
