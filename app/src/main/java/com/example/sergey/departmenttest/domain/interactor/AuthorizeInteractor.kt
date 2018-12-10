package com.example.sergey.departmenttest.domain.interactor

import com.example.sergey.departmenttest.data.repository.EmployeesRepository
import com.example.sergey.departmenttest.domain.model.AuthorizedUser
import com.example.sergey.departmenttest.domain.model.OperationStatus

interface AuthorizeInteractor {
    suspend fun getAuthorizedUser(): AuthorizedUser?
    suspend fun authorizeUser(login: String, password: String): OperationStatus
}

class AuthorizeInteractorImpl(
        private val repository: EmployeesRepository
) : AuthorizeInteractor {
    override suspend fun getAuthorizedUser() = repository.getAuthorizedUser()
    override suspend fun authorizeUser(login: String, password: String) = repository.authorizeUser(login, password)
}