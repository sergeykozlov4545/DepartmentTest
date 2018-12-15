package com.example.sergey.departmenttest.data.repository

import com.example.sergey.departmenttest.data.remote.ServiceApi
import com.example.sergey.departmenttest.data.model.*

interface DepartmentsRepository {
    fun setAuthorizedUser(user: AuthorizedUser)

    suspend fun getDepartmentsInfo(): List<TreeElement>
    suspend fun setDepartmentsInfo(info: List<TreeElement>)

    suspend fun getEmployee(id: Long): Employee?
    suspend fun getEmployeePhoto(employeeId: Long): DownloadImage

    fun clear()
}

class DepartmentsRepositoryImpl(
        private val serviceApi: ServiceApi
) : DepartmentsRepository {
    private lateinit var user: AuthorizedUser

    private var info: MutableList<TreeElement>? = null
    private val imageCache: MutableMap<Long, DownloadImage> = HashMap()

    override fun setAuthorizedUser(user: AuthorizedUser) {
        this.user = user
    }

    override suspend fun getDepartmentsInfo(): List<TreeElement> {
        return info.takeIf { it != null } ?: run {
            val rootDepartment = serviceApi.getDepartments(user.login, user.password).await()
            return@run DepartmentsInfoBuilder.build(rootDepartment).let {
                info = it
                return@let info!!
            }
        }
    }

    override suspend fun setDepartmentsInfo(info: List<TreeElement>) {
        this.info?.clear()
        this.info?.addAll(info)
    }

    override suspend fun getEmployee(id: Long) = info?.filter { it is EmployeeElement }
            ?.map { (it as EmployeeElement).employee }
            ?.find { it.id == 1L }

    override suspend fun getEmployeePhoto(employeeId: Long): DownloadImage {
        return imageCache[employeeId] ?: run {
            val response = serviceApi.getEmployeePhoto(user.login, user.password, employeeId).await()
            return@run DownloadImage(response.bytes()).apply {
                imageCache[employeeId] = this
            }
        }
    }

    override fun clear() {
        info?.clear()
        imageCache.clear()
    }
}

object DepartmentsInfoBuilder {
    private var currentVisitedTime: Int = 0
    private var info: MutableList<TreeElement> = ArrayList()

    fun build(department: Department): MutableList<TreeElement> {
        build(department, "", 0)
        return info
    }

    private fun build(
            department: Department,
            parentId: String,
            depth: Int
    ) {
        val timeStart = currentVisitedTime
        val departmentElement = DepartmentElement(
                id = department.id,
                name = department.name,
                depth = depth,
                parentId = parentId
        )
        currentVisitedTime++

        info.add(departmentElement)
        for (subDepartment in department.subDepartments) {
            build(subDepartment, departmentElement.id, depth + 1)
        }
        for (employee in department.employees) {
            val employeeElement = EmployeeElement(
                    id = employee.id,
                    employee = employee,
                    depth = depth + 1,
                    timeRange = currentVisitedTime..currentVisitedTime + 1,
                    parentId = departmentElement.id
            )
            currentVisitedTime += 2
            info.add(employeeElement)
        }

        departmentElement.timeRange = timeStart..currentVisitedTime
        currentVisitedTime++
    }
}