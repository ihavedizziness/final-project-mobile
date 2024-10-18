package com.example.final_project_mobile.navigation

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen

open class AppNavigator(
    fragment: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager = fragment.supportFragmentManager,
) : AppNavigator(fragment, containerId, fragmentManager) {

    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        when (nextFragment) {
            is FragmentTransactionAnimation -> nextFragment.apply { fragmentTransaction.applyAnimation() }
        }
        fragmentTransaction.setReorderingAllowed(true)
    }

    override fun back() {
        getLastDialogFragment()?.let { return it.dismiss() }
        super.back()
    }

    override fun forward(command: Forward) {
        val screen = command.screen as? FragmentScreen
        if (screen?.clearContainer == true) {
            getLastDialogFragment()?.dismiss()
        }
        when (screen) {
            is DialogScreen -> showDialogFragment(screen)
            else -> super.forward(command)
        }
    }

    protected open fun showDialogFragment(screen: DialogScreen) {
        val dialogFragment = screen.createDialog(fragmentFactory)
        dialogFragment.show(fragmentManager, null)
    }

    protected open fun getLastDialogFragment(): DialogFragment? {
        return fragmentManager.fragments.filterIsInstance<DialogFragment>().lastOrNull()
    }
}
