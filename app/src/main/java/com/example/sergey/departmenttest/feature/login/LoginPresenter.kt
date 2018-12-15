package com.example.sergey.departmenttest.feature.login

import com.example.sergey.departmenttest.data.repository.AuthRepository
import com.example.sergey.departmenttest.data.repository.DepartmentsRepository
import com.example.sergey.departmenttest.data.model.AuthorizedUser
import com.example.sergey.departmenttest.exception.OperationException
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter

interface LoginPresenter : BasePresenter<LoginView> {
    fun authorizeUser(login: String, password: String)
}

class LoginPresenterImpl(
        override val view: LoginView,
        private val authRepository: AuthRepository,
        private val departmentsRepository: DepartmentsRepository
) : Presenter<LoginView>(view), LoginPresenter {
    override fun authorizeUser(login: String, password: String) = runInCoroutine {
        val status = authRepository.authorizeUser(login, password)
        if (status.isSuccess) {
            departmentsRepository.setAuthorizedUser(AuthorizedUser(login, password))
            view.openMainActivity()
        } else {
            throw OperationException(status.message)
        }
    }
}