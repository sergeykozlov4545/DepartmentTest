package com.example.sergey.departmenttest.feature.splash

import com.example.sergey.departmenttest.domain.interactor.AuthorizeInteractor
import com.example.sergey.departmenttest.extansion.runInCoroutine
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter
import kotlinx.coroutines.CoroutineScope

interface SplashPresenter : BasePresenter<SplashView> {
    fun loadAuthorizedUser()
    fun checkAuthorizedUser()
}

class SplashPresenterImpl(
        override val view: SplashView,
        private val authorizeInteractor: AuthorizeInteractor
) : Presenter<SplashView>(view), SplashPresenter {

    override fun loadAuthorizedUser() {
        view.takeIf { it is CoroutineScope }
                ?.also { (it as CoroutineScope).runInCoroutine(this::loadAuthorizedUserAsync) }
    }

    private suspend fun loadAuthorizedUserAsync() {
        view.onGetAuthorizedUser(authorizeInteractor.getAuthorizedUser())
    }

    override fun checkAuthorizedUser() {
        view.takeIf { it is CoroutineScope }
                ?.also { (it as CoroutineScope).runInCoroutine(this::checkAuthorizedUserAsync) }
    }

    private suspend fun checkAuthorizedUserAsync() {
        val authorizedUser = authorizeInteractor.getAuthorizedUser()
        if (authorizedUser == null) {
            view.openLoginActivity()
            return
        }

        val status = authorizeInteractor.authorizeUser(authorizedUser.login, authorizedUser.password)
        if (status.isSuccess) {
            view.openMainActivity()
            return
        }

        view.openLoginActivity(authorizedUser, status.message)
    }
}