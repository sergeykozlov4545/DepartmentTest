package com.example.sergey.departmenttest.feature.splash

import com.example.sergey.departmenttest.data.repository.AuthRepository
import com.example.sergey.departmenttest.data.repository.DepartmentsRepository
import com.example.sergey.departmenttest.extansion.runInScope
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter

interface SplashPresenter : BasePresenter<SplashView> {
    fun checkAuthorizedUser()
    fun requestOpenLoginActivity()
}

class SplashPresenterImpl(
        override val view: SplashView,
        private val authRepository: AuthRepository,
        private val departmentsRepository: DepartmentsRepository
) : Presenter<SplashView>(view), SplashPresenter {

    override fun checkAuthorizedUser() = runInScope {
        val user = authRepository.getAuthorizedUser()
        if (user == null) {
            view.openLoginActivity()
            return@runInScope
        }

        val status = authRepository.authorizeUser(user.login, user.password)
        if (status.isSuccess) {
            departmentsRepository.setAuthorizedUser(user)
            view.openMainActivity()
            return@runInScope
        }

        view.openLoginActivity(user, status.message)
    }

    override fun requestOpenLoginActivity() = runInScope {
        val user = authRepository.getAuthorizedUser()
        view.openLoginActivity(user, "")
    }
}