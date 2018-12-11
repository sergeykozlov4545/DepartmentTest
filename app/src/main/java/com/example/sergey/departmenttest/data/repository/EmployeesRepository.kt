package com.example.sergey.departmenttest.data.repository

import com.example.sergey.departmenttest.data.local.PreferenceManager
import com.example.sergey.departmenttest.data.remote.ServiceApi
import com.example.sergey.departmenttest.domain.model.AuthorizedUser
import com.example.sergey.departmenttest.domain.model.Department
import com.example.sergey.departmenttest.domain.model.Employee
import com.example.sergey.departmenttest.domain.model.OperationStatus

interface EmployeesRepository {
    suspend fun getAuthorizedUser(): AuthorizedUser?
    suspend fun authorizeUser(login: String, password: String): OperationStatus

    suspend fun getDepartments(): Department?
    suspend fun getEmployee(id: Long): Employee?
}

class EmployeesRepositoryImpl(
        private val serviceApi: ServiceApi,
        private val preferenceManager: PreferenceManager
) : EmployeesRepository {

    private var authorizedUser: AuthorizedUser? = null
    private var departments: Department? = null

    override suspend fun getAuthorizedUser() = authorizedUser.takeIf { it != null }
            ?: run {
                val login = preferenceManager.getString("login", "")
                val password = preferenceManager.getString("password", "")
                return@run if (login.isEmpty() || password.isEmpty()) null else AuthorizedUser(login, password)
            }

    override suspend fun authorizeUser(login: String, password: String): OperationStatus {
        val status = serviceApi.hello(login, password).await()
        if (status.isSuccess) {
            authorizedUser = AuthorizedUser(login, password)
            preferenceManager.putString("login", login)
            preferenceManager.putString("password", password)
        }
        return status
    }

    override suspend fun getDepartments() = departments.takeIf { it != null } ?: loadDepartments()

    private suspend fun loadDepartments() =
            authorizedUser.takeIf { it != null }
                    ?.let { serviceApi.getDepartments(it.login, it.password).await() }
                    ?.also { departments = it }

    override suspend fun getEmployee(id: Long) = departments.takeIf { it != null }?.let { dfs(it, id) }

    private suspend fun dfs(department: Department, id: Long): Employee? {
        if (department.subDepartments.isEmpty()) {
            for (employee in department.employees) {
                if (employee.id == id) {
                    return employee
                }
            }
            return null
        }
        for (subDepartment in department.subDepartments) {
            val employee = dfs(subDepartment, id)
            if (employee != null) {
                return employee
            }
        }
        return null
    }
}