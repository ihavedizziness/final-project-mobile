package com.example.final_project_mobile.navigation

import androidx.fragment.app.FragmentTransaction
import com.example.final_project_mobile.R

sealed interface FragmentTransactionAnimation {

    fun FragmentTransaction.applyAnimation() = Unit

    interface NoAnimation : FragmentTransactionAnimation

    interface SlideFromRight : FragmentTransactionAnimation {
        override fun FragmentTransaction.applyAnimation() {
            setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    interface Fade : FragmentTransactionAnimation {
        override fun FragmentTransaction.applyAnimation() {
            setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
        }
    }
}
