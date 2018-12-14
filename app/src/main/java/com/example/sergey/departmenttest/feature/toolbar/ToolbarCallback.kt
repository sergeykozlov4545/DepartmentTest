package com.example.sergey.departmenttest.feature.toolbar

import android.support.annotation.DrawableRes
import android.support.annotation.MenuRes
import android.view.MenuItem

interface ToolbarCallback {
    fun setBackAction(@DrawableRes drawableId: Int, action: () -> Unit)
    fun updateTitle(title: String)
    fun setMenu(@MenuRes menuResId: Int, clickListener: (item: MenuItem) -> Boolean)
}