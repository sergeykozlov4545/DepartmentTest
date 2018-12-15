package com.example.sergey.departmenttest.feature.main

import com.example.sergey.departmenttest.data.model.Employee
import com.example.sergey.departmenttest.feature.core.BaseView

interface MainView : BaseView {
    fun onContentLoaded()
    fun openDetailsScreen(employee: Employee)
    fun openLoginActivity()
}