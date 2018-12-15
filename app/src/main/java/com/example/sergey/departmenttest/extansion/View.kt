package com.example.sergey.departmenttest.extansion

import android.view.View

fun View?.showView() {
    this ?: return
    visibility = View.VISIBLE
}

fun View?.hideView(onlyInvisible: Boolean = false) {
    this ?: return
    visibility = if (onlyInvisible) View.INVISIBLE else View.GONE
}

