package com.example.sergey.departmenttest.domain.model

sealed class TreeElement(
        var isVisible: Boolean = false,
        var depth: Int = 0,
        var timeRange: IntRange = 0..0,
        var parentVisitedTime: Int = 0
)

class DepartmentElement(
        var isOpened: Boolean = false,
        val name: String = "",
        depth: Int = 0,
        parentVisitedTime: Int = 0
) : TreeElement(depth = depth, parentVisitedTime = parentVisitedTime)

class EmployeeElement(
        val employee: Employee = Employee(),
        depth: Int = 0,
        timeRange: IntRange = 0..0,
        parentVisitedTime: Int = 0
) : TreeElement(depth = depth, timeRange = timeRange, parentVisitedTime = parentVisitedTime)


