package com.example.sergey.departmenttest.data.repository

import com.example.sergey.departmenttest.data.local.PreferenceManager
import com.example.sergey.departmenttest.data.remote.ServiceApi
import com.example.sergey.departmenttest.domain.model.AuthorizedUser
import com.example.sergey.departmenttest.domain.model.OperationStatus

interface EmployeesRepository {
    suspend fun getAuthorizedUser(): AuthorizedUser?
    suspend fun authorizeUser(login: String, password: String): OperationStatus
}

class EmployeesRepositoryImpl(
        private val serviceApi: ServiceApi,
        private val preferenceManager: PreferenceManager
) : EmployeesRepository {

    private var authorizedUser: AuthorizedUser? = null

    override suspend fun getAuthorizedUser() = authorizedUser.takeIf { it != null }
            ?.let { it }
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
}