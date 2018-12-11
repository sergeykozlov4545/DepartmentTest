package com.example.sergey.departmenttest.domain.interactor

import com.example.sergey.departmenttest.data.repository.EmployeesRepository
import com.example.sergey.departmenttest.domain.model.Department
import com.example.sergey.departmenttest.domain.model.Employee

interface DepartmentsInteractor {
    suspend fun getDepartments(): Department?
    suspend fun getEmployee(id: Long): Employee?
}

class DepartmentsInteractorImpl(
        private val employeesRepository: EmployeesRepository
) : DepartmentsInteractor {
    override suspend fun getDepartments() = employeesRepository.getDepartments()
    override suspend fun getEmployee(id: Long) = employeesRepository.getEmployee(id)
}