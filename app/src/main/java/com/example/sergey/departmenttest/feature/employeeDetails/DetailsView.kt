package com.example.sergey.departmenttest.feature.employeeDetails

import com.example.sergey.departmenttest.data.model.DownloadImage
import com.example.sergey.departmenttest.data.model.Employee
import com.example.sergey.departmenttest.feature.core.BaseView

interface DetailsView : BaseView {
    fun onGetEmployee(employee: Employee, downloadImage: DownloadImage)
}