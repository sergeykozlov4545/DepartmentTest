package com.example.sergey.departmenttest.feature.core

import kotlinx.coroutines.CoroutineScope

interface ErrorView {
    fun onError(exception: Exception)
}

interface BaseView : ErrorView {
    val scope: CoroutineScope
}