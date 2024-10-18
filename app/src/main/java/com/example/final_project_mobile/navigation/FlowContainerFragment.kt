package com.example.final_project_mobile.navigation

import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.final_project_mobile.R
import com.example.final_project_mobile.core.BaseFragment
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Screen

abstract class FlowContainerFragment : BaseFragment(R.layout.fragment_flow_container),
    NavigationFlowFragment {

    abstract val startDestination: Screen
    abstract val isShowingBottomNavigation: Boolean
    abstract val isRootFlowController: Boolean
    override var navStackHandler: NavStackHandler? = null

    private val currentFragment get() = childFragmentManager.findFragmentById(R.id.localContainer) as? BaseFragment
    private val navigator: Navigator by lazy { Navigation() }
    private val navStackItem get() = NavStackItem(isRootFlowController, isShowingBottomNavigation)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navStackHandler = context as? NavStackHandler
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onStartDestination()
    }

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(navigator)
        navStackHandler?.pushNavigationController(navStackItem)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        navStackHandler?.popNavigationController()
        super.onPause()
    }

    override fun onDestroy() {
        parentFragmentManager.fragments.lastOrNull { it.isVisible }?.onResume()
        super.onDestroy()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

    open fun onStartDestination() {
        if (childFragmentManager.fragments.isEmpty()) navigator.setLaunchScreen(startDestination)
    }

    inner class Navigation : AppNavigator(requireActivity(), R.id.localContainer, childFragmentManager) {
        override fun activityBack() = router.finishFlow()
    }
}
