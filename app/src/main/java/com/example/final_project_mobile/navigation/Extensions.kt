package com.example.final_project_mobile.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.final_project_mobile.core.utils.className
import com.github.terrakok.cicerone.BackTo
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import kotlin.reflect.KProperty
import kotlin.reflect.full.createInstance

fun Navigator.setLaunchScreen(screen: Screen) {
    applyCommands(arrayOf(BackTo(null), Replace(screen)))
}

inline fun <reified F : Fragment> bottomNavScreen(tab: Enum<*>, clearContainer: Boolean = true) = object :
    FragmentScreen {
    override val screenKey: String get() = tab.name
    override val clearContainer: Boolean = clearContainer
    override fun createFragment(factory: FragmentFactory): Fragment = F::class.createInstance()
    operator fun getValue(thisRef: Any?, property: KProperty<*>): FragmentScreen = this
}

inline fun <reified F : Fragment> fragmentScreen(clearContainer: Boolean = false) = object : FragmentScreen {
    override val screenKey: String get() = className<F>()
    override val clearContainer: Boolean = clearContainer
    override fun createFragment(factory: FragmentFactory): Fragment = F::class.createInstance()
    operator fun getValue(thisRef: Any?, property: KProperty<*>): FragmentScreen = this
}

inline fun <reified F : Fragment> fragmentScreenArg(arg: ScreenArgs, clearContainer: Boolean = false) = object : FragmentScreen {
    override val clearContainer: Boolean = clearContainer
    override val screenKey: String get() = className<F>()
    private fun putArgs(fragment: F) = fragment.setArguments(bundleOf(screenKey to arg))
    override fun createFragment(factory: FragmentFactory): Fragment = F::class.createInstance().apply(::putArgs)
    operator fun getValue(thisRef: Any?, property: KProperty<*>): FragmentScreen = this
}

inline fun <reified F : DialogFragment> dialogScreen(clearContainer: Boolean = false) = object : DialogScreen {
    override val screenKey: String get() = className<F>()
    override val clearContainer: Boolean = clearContainer
    override fun createDialog(factory: FragmentFactory): DialogFragment = F::class.createInstance()
    operator fun getValue(thisRef: Any?, property: KProperty<*>): DialogScreen = this
}

inline fun <reified F : DialogFragment> dialogScreenArg(arg: ScreenArgs, clearContainer: Boolean = false) = object : DialogScreen {
    override val clearContainer: Boolean = clearContainer
    override val screenKey: String get() = className<F>()
    private fun putArgs(fragment: F) = fragment.setArguments(bundleOf(screenKey to arg))
    override fun createDialog(factory: FragmentFactory): DialogFragment = F::class.createInstance().apply(::putArgs)
    operator fun getValue(thisRef: Any?, property: KProperty<*>): DialogScreen = this
}
