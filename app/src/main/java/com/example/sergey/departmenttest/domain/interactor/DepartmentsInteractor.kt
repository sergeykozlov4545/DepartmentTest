package com.example.sergey.departmenttest.domain.interactor

import com.example.sergey.departmenttest.data.repository.EmployeesRepository
import com.example.sergey.departmenttest.domain.model.DepartmentElement
import com.example.sergey.departmenttest.domain.model.Employee
import com.example.sergey.departmenttest.domain.model.TreeElement

interface DepartmentsInteractor {
    suspend fun getTreeElements(): List<TreeElement>
    suspend fun toggleDepartmentElement(element: DepartmentElement): List<TreeElement>
    suspend fun getEmployee(id: Long): Employee?
}

class DepartmentsInteractorImpl(
        private val employeesRepository: EmployeesRepository
) : DepartmentsInteractor {
    override suspend fun getTreeElements() = employeesRepository.getTreeElements()
    override suspend fun toggleDepartmentElement(element: DepartmentElement) =
            employeesRepository.toggleDepartmentElement(element)

    override suspend fun getEmployee(id: Long) = employeesRepository.getEmployee(id)
}