package com.example.sergey.departmenttest.domain.interactor

import com.example.sergey.departmenttest.domain.model.Employee
import com.example.sergey.departmenttest.feature.core.BaseView

interface DepartmentView : BaseView {
    fun onGetEmployee(employee: Employee)
}