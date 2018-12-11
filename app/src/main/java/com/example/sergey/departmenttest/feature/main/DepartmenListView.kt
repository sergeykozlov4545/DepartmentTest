package com.example.sergey.departmenttest.feature.main

import com.example.sergey.departmenttest.domain.model.Employee
import com.example.sergey.departmenttest.domain.model.TreeElement
import com.example.sergey.departmenttest.feature.core.BaseView

interface DepartmentListView : BaseView {
    fun onGetTreeElements(elements: List<TreeElement>)
    fun openDetalisationEmployee(employee: Employee)
}