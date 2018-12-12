package com.example.sergey.departmenttest.data.repository

import com.example.sergey.departmenttest.data.local.PreferenceManager
import com.example.sergey.departmenttest.data.remote.ServiceApi
import com.example.sergey.departmenttest.domain.model.*
import com.example.sergey.departmenttest.extansion.between

interface EmployeesRepository {
    suspend fun getAuthorizedUser(): AuthorizedUser?
    suspend fun authorizeUser(login: String, password: String): OperationStatus

    suspend fun getTreeElements(): List<TreeElement>
    suspend fun toggleDepartmentElement(element: DepartmentElement): List<TreeElement>

    suspend fun getEmployee(id: Long): Employee?
    suspend fun getEmployeePhoto(employeeId: Long): DownloadImage?
}

class EmployeesRepositoryImpl(
        private val serviceApi: ServiceApi,
        private val preferenceManager: PreferenceManager
) : EmployeesRepository {

    private var authorizedUser: AuthorizedUser? = null

    private var allEmployees: MutableList<Employee>? = null
    private var treeElements: MutableList<TreeElement>? = null
    private var currentVisitedTime: Int = 0

    private val imageCache: MutableMap<Long, DownloadImage> = HashMap()

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

    override suspend fun getTreeElements() = treeElements.takeIf { it != null }
            ?.filter(TreeElement::isVisible) ?: loadTreeElements()

    override suspend fun toggleDepartmentElement(element: DepartmentElement): List<TreeElement> {
        if (element.isOpened) {
            treeElements.takeIf { it != null }
                    ?.filter {
                        it.timeRange.between(element.timeRange)
                    }?.forEach {
                        it.isVisible = false
                        if (it is DepartmentElement) {
                            it.isOpened = false
                        }
                    }
            element.isVisible = true
        } else {
            treeElements.takeIf { it != null }
                    ?.filter { it.parentId == element.id }
                    ?.forEach { it.isVisible = true }
            element.isOpened = true
        }
        return treeElements.takeIf { it != null }?.filter(TreeElement::isVisible) ?: emptyList()
    }

    override suspend fun getEmployee(id: Long) = allEmployees?.find { it.id == id }

    override suspend fun getEmployeePhoto(employeeId: Long): DownloadImage? {
        return imageCache[employeeId]
                ?: authorizedUser.takeIf { it != null }
                        ?.let { serviceApi.getEmployeePhoto(it.login, it.password, employeeId).await() }
                        ?.run {
                            val image = DownloadImage(bytes())
                            imageCache[employeeId] = image
                            return@run image
                        }
    }

    private suspend fun loadTreeElements(): List<TreeElement> {
        return authorizedUser.takeIf { it != null }
                ?.let { serviceApi.getDepartments(it.login, it.password).await() }
                ?.run {
                    treeElements = mutableListOf()
                    allEmployees = mutableListOf()
                    currentVisitedTime = 0
                    buildTree(this)
                    treeElements!!.first().isVisible = true

                    return@run treeElements!!.filter(TreeElement::isVisible)
                } ?: emptyList()
    }

    private suspend fun buildTree(
            department: Department,
            parentId: String = "",
            depth: Int = 0
    ) {
        val timeStart = currentVisitedTime
        val departmentElement = DepartmentElement(
                id = department.id,
                name = department.name,
                depth = depth,
                parentId = parentId
        )
        currentVisitedTime++

        treeElements?.add(departmentElement)
        for (subDepartment in department.subDepartments) {
            buildTree(subDepartment, departmentElement.id, depth + 1)
        }
        for (employee in department.employees) {
            allEmployees?.add(employee)

            val employeeElement = EmployeeElement(
                    id = employee.id,
                    employee = employee,
                    depth = depth + 1,
                    timeRange = currentVisitedTime..currentVisitedTime + 1,
                    parentId = departmentElement.id
            )
            currentVisitedTime += 2
            treeElements?.add(employeeElement)
        }

        departmentElement.timeRange = timeStart..currentVisitedTime
        currentVisitedTime++
    }
}