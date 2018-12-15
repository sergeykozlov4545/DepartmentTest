package com.example.sergey.departmenttest.data.repository

import com.example.sergey.departmenttest.data.local.PreferenceManager
import com.example.sergey.departmenttest.data.remote.ServiceApi
import com.example.sergey.departmenttest.data.model.AuthorizedUser
import com.example.sergey.departmenttest.data.model.OperationStatus
import kotlinx.coroutines.delay

interface AuthRepository {
    suspend fun getAuthorizedUser(): AuthorizedUser?
    suspend fun authorizeUser(login: String, password: String): OperationStatus
    suspend fun logout()
}

class AuthRepositoryImpl(
        private val serviceApi: ServiceApi,
        private val preferenceManager: PreferenceManager
) : AuthRepository {
    private var authorizedUser: AuthorizedUser? = null

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
        delay(2000L)
        return status
    }

    override suspend fun logout() {
        preferenceManager.apply {
            putString("login", "")
            putString("password", "")
        }
        authorizedUser = null
    }
}

