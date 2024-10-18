package com.example.final_project_mobile.navigation

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel

abstract class ScreenArgs : Parcelable {
    @IgnoredOnParcel
    open val key = this::class.simpleName.orEmpty()
}
