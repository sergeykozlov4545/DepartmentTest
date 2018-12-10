package com.example.sergey.departmenttest.feature.core

import java.lang.Exception

interface ErrorView {
    fun onError(exception: Exception)
}

interface BaseView : ErrorView {

}