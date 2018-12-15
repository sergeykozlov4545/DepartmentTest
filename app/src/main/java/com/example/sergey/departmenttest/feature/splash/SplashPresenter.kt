package com.example.sergey.departmenttest.feature.splash

import com.example.sergey.departmenttest.data.repository.AuthRepository
import com.example.sergey.departmenttest.data.repository.DepartmentsRepository
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter

interface SplashPresenter : BasePresenter<SplashView> {
    fun loadAuthorizedUser()
    fun checkAuthorizedUser()
}

class SplashPresenterImpl(
        override val view: SplashView,
        private val authRepository: AuthRepository,
        private val departmentsRepository: DepartmentsRepository
) : Presenter<SplashView>(view), SplashPresenter {

    override fun loadAuthorizedUser() = runInCoroutine {
        view.onGetAuthorizedUser(authRepository.getAuthorizedUser())
    }

    override fun checkAuthorizedUser() = runInCoroutine {
        val user = authRepository.getAuthorizedUser()
        if (user == null) {
            view.openLoginActivity()
            return@runInCoroutine
        }

        val status = authRepository.authorizeUser(user.login, user.password)
        if (status.isSuccess) {
            departmentsRepository.setAuthorizedUser(user)
            view.openMainActivity()
            return@runInCoroutine
        }

        view.openLoginActivity(user, status.message)
    }
}