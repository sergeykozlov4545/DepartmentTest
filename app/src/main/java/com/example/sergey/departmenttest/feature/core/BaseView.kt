package com.example.sergey.departmenttest.feature.core

import java.lang.Exception

interface BaseView

interface ErrorView : BaseView {
    fun onError(exception: Exception)
}