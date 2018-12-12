package com.example.sergey.departmenttest.domain.model

import com.google.gson.annotations.SerializedName

data class Department(
        @SerializedName("ID") val id: Long = 0,
        @SerializedName("Name") val name: String = "",
        @SerializedName("Departments") val subDepartments: List<Department> = emptyList(),
        @SerializedName("Employees") val employees: List<Employee> = emptyList()
)