package com.example.sergey.departmenttest.domain.interactor

import com.example.sergey.departmenttest.domain.model.Department
import com.example.sergey.departmenttest.feature.core.BaseView

interface DepartmentListView : BaseView {
    fun onGetDepartments(department: Department)
}