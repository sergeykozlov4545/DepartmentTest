package com.example.sergey.departmenttest.feature.departmentList

import com.example.sergey.departmenttest.data.model.Employee
import com.example.sergey.departmenttest.data.model.TreeElement
import com.example.sergey.departmenttest.feature.core.BaseView

interface DepartmentListView : BaseView {
    fun onGetTreeElements(elements: List<TreeElement>)
    fun openDetailsScreen(employee: Employee)
}