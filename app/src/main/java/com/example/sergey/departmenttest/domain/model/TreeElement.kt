package com.example.sergey.departmenttest.domain.model

sealed class TreeElement(
        val id: String,
        val parentId: String = "",
        val depth: Int = 0,
        var timeRange: IntRange = 0..0,
        var isVisible: Boolean = false
)

class DepartmentElement(
        val name: String = "",
        var isOpened: Boolean = false,
        id: Long,
        parentId: String = "",
        depth: Int = 0
) : TreeElement(id = "D_$id", parentId = parentId, depth = depth)

class EmployeeElement(
        val employee: Employee = Employee(),
        id: Long,
        parentId: String = "",
        depth: Int = 0,
        timeRange: IntRange = 0..0
) : TreeElement(id = "E_$id", parentId = parentId, depth = depth, timeRange = timeRange)


