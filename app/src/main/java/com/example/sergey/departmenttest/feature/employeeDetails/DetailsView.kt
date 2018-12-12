package com.example.sergey.departmenttest.feature.employeeDetails

import com.example.sergey.departmenttest.domain.model.DownloadImage
import com.example.sergey.departmenttest.domain.model.Employee
import com.example.sergey.departmenttest.feature.core.BaseView

interface DetailsView : BaseView {
    fun onGetEmployee(employee: Employee)
    fun onGetEmployeePhoto(downloadImage: DownloadImage)
}