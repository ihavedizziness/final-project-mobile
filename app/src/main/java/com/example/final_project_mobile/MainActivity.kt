package com.example.final_project_mobile

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.doOnAttach
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.final_project_mobile.core.BaseFragment
import com.example.final_project_mobile.main.BottomNavItem
import com.example.final_project_mobile.main.BottomNavScreens
import com.example.final_project_mobile.main.BottomNavigationContent
import com.example.final_project_mobile.navigation.AppNavigator
import com.example.final_project_mobile.navigation.FragmentTransactionAnimation
import com.example.final_project_mobile.navigation.NavStackHandler
import com.example.final_project_mobile.navigation.NavStackItem
import com.example.final_project_mobile.ui.setContentWithTheme
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Replace
import org.koin.android.ext.android.inject
import java.util.Stack

class MainActivity :
    AppCompatActivity(R.layout.activity_main),
    NavStackHandler,
    ActivityEnvRouter {

    override val navControllerStack: Stack<NavStackItem> = Stack()
    private val bottomNavigationCompose: ComposeView by lazy { findViewById(R.id.bottomNavigationComposeView) }

    private val navigatorHolder: NavigatorHolder by inject()
    private val navigator: Navigator by lazy { Navigation() }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(owner = this, ActivityOnBackPressedCallback())

        bottomNavigationCompose.setContentWithTheme {
            viewModel.bottomNavigationState.collectAsState().value.let {
                BottomNavigationContent(it, ::goToTab)
            }
        }

        viewModel.router.startFlow(BottomNavScreens.OnboardingScreen)
        goToTab(BottomNavItem.Home)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun goToTab(tab: BottomNavItem) = onBottomNavItemSelected(item = tab)

    override fun getCurrentTab(): BottomNavItem = viewModel.currentBottomNavItem.value

    private fun onBottomNavItemSelected(item: BottomNavItem) {
        if (viewModel.currentBottomNavItem.value == item) {
            viewModel.router.backTo(null)
        }
        onNavigationItemResSelect()
        viewModel.onBottomNavItemClicked(item)
        selectBottomNavItem(item)
    }

    private fun selectBottomNavItem(item: BottomNavItem) {
        val currentFlowContainerFragments = supportFragmentManager.fragments.filter { !it.isHidden }
        val newFlowContainerFragment: Fragment? = supportFragmentManager.findFragmentByTag(item.name)

        supportFragmentManager.beginTransaction().apply {
            (newFlowContainerFragment as? FragmentTransactionAnimation)?.apply { applyAnimation() }

            if (newFlowContainerFragment == null) {
                add(
                    R.id.globalContainer,
                    item.screen().createFragment(supportFragmentManager.fragmentFactory),
                    item.name,
                )
            }
            currentFlowContainerFragments.forEach { setMaxLifecycle(it, Lifecycle.State.STARTED).hide(it) }
            newFlowContainerFragment?.let { setMaxLifecycle(it, Lifecycle.State.RESUMED).show(it) }
        }.commitNow()
    }

    private fun onNavigationItemResSelect() = currentNavigationItem?.let {
        if (!it.isRootFlowController) popNavigationController()
    }

    override fun pushNavigationController(item: NavStackItem) {
        showBottomNavigation(item.isShowBottomNavigation)

        if (navControllerStack.contains(item)) {
            navControllerStack.remove(item)
        }

        navControllerStack.push(item)
    }

    private fun showBottomNavigation(show: Boolean) {
        bottomNavigationCompose.doOnAttach {
            bottomNavigationCompose.isVisible = show
        }
    }

    override fun popNavigationController() {
        if (navControllerStack.isNotEmpty()) {
            navControllerStack.pop()

            val isVisible = currentNavigationItem?.isShowBottomNavigation ?: true
            showBottomNavigation(isVisible)
        }
    }

    inner class Navigation : AppNavigator(this, R.id.globalContainer, supportFragmentManager) {
        override fun replace(command: Replace) {
            BottomNavItem.valueOfOrNull(command.screen.screenKey)?.let { return goToTab(it) }
            super.replace(command)
        }
    }

    inner class ActivityOnBackPressedCallback : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val currentVisibleGraphManager = supportFragmentManager.fragments.lastOrNull { !it.isHidden }?.childFragmentManager
            val currentFragment = currentVisibleGraphManager?.fragments?.lastOrNull { !it.isHidden } as? BaseFragment
            currentFragment?.onBackPressed() ?: onBackPressedDispatcher.onBackPressed()
        }
    }
}
