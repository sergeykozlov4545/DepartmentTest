package com.example.sergey.departmenttest.feature.toolbar

import android.support.annotation.DrawableRes

interface ToolbarCallback {
    fun setBackAction(@DrawableRes drawableId: Int, action: () -> Unit)
    fun updateTitle(title: String)
}