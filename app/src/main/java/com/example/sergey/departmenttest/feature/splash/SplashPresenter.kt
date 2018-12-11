package com.example.sergey.departmenttest.feature.splash

import com.example.sergey.departmenttest.domain.interactor.AuthorizeInteractor
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter

interface SplashPresenter : BasePresenter<SplashView> {
    fun loadAuthorizedUser()
    fun checkAuthorizedUser()
}

class SplashPresenterImpl(
        override val view: SplashView,
        private val authorizeInteractor: AuthorizeInteractor
) : Presenter<SplashView>(view), SplashPresenter {

    override fun loadAuthorizedUser() = runInCoroutine {
        view.onGetAuthorizedUser(authorizeInteractor.getAuthorizedUser())
    }

    override fun checkAuthorizedUser() = runInCoroutine {
        val authorizedUser = authorizeInteractor.getAuthorizedUser()
        if (authorizedUser == null) {
            view.openLoginActivity()
            return@runInCoroutine
        }

        val status = authorizeInteractor.authorizeUser(authorizedUser.login, authorizedUser.password)
        if (status.isSuccess) {
            view.openMainActivity()
            return@runInCoroutine
        }

        view.openLoginActivity(authorizedUser, status.message)
    }
}