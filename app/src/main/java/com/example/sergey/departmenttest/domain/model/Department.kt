package com.example.sergey.departmenttest.domain.model

data class Department(
    val id: Long = 0,
    val name: String = "",
    val subDepartments: List<Department> = emptyList(),
    val employees: List<Employee> = emptyList()
)