package com.example.sergey.departmenttest.feature.splash

import com.example.sergey.departmenttest.domain.interactor.AuthorizeInteractor
import com.example.sergey.departmenttest.extansion.runInCoroutine
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter
import kotlinx.coroutines.CoroutineScope

interface SplashPresenter : BasePresenter<SplashView> {
    fun checkAuthorizedUser()
}

class SplashPresenterImpl(
        private val view: SplashView,
        private val authorizeInteractor: AuthorizeInteractor
) : Presenter<SplashView>(view), SplashPresenter {

    override fun checkAuthorizedUser() {
        val scope = view as? CoroutineScope ?: return

        scope.runInCoroutine {
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
}