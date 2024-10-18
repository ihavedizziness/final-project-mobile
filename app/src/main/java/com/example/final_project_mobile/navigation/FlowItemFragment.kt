package com.example.final_project_mobile.navigation

import android.content.Context
import com.example.final_project_mobile.ActivityEnvRouter
import com.example.final_project_mobile.core.BaseFragment

abstract class FlowItemFragment : BaseFragment() {

    protected var activityEnvironmentRouter: ActivityEnvRouter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityEnvironmentRouter = context as? ActivityEnvRouter
    }

}
