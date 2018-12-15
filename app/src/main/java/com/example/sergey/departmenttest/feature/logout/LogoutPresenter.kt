package com.example.sergey.departmenttest.feature.logout

import com.example.sergey.departmenttest.data.repository.AuthRepository
import com.example.sergey.departmenttest.data.repository.DepartmentsRepository
import com.example.sergey.departmenttest.extansion.runInScope
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter

interface LogoutPresenter : BasePresenter<LogoutView> {
    fun logout()
}

class LogoutPresenterImpl(
        override val view: LogoutView,
        private val authRepository: AuthRepository,
        private val departmentsRepository: DepartmentsRepository
) : Presenter<LogoutView>(view), LogoutPresenter {

    override fun logout() = runInScope {
        authRepository.logout()
        departmentsRepository.clear()
        view.openLoginActivity()
    }
}