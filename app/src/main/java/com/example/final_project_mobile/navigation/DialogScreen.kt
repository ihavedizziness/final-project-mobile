package com.example.final_project_mobile.navigation

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.Creator

interface DialogScreen : Screen {

    val clearContainer: Boolean get() = true
    fun createDialog(factory: FragmentFactory): DialogFragment

    companion object {
        operator fun invoke(
            key: String? = null,
            clearContainer: Boolean = false,
            fragmentCreator: Creator<FragmentFactory, DialogFragment>,
        ) = object : DialogScreen {
            override val screenKey = key ?: fragmentCreator::class.java.name
            override val clearContainer = clearContainer
            override fun createDialog(factory: FragmentFactory) = fragmentCreator.create(factory)
        }
    }
}
