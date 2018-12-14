package com.example.sergey.departmenttest.feature.logout

import com.example.sergey.departmenttest.domain.interactor.AuthorizeInteractor
import com.example.sergey.departmenttest.exception.OperationException
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter

interface LogoutPresenter : BasePresenter<LogoutView> {
    fun logout()
}

class LogoutPresenterImpl(
        override val view: LogoutView,
        private val authorizeInteractor: AuthorizeInteractor
) : Presenter<LogoutView>(view), LogoutPresenter {

    override fun logout() = runInCoroutine {
        val status = authorizeInteractor.logout()
        if (status.isSuccess) {
            view.openLoginActivity()
        } else {
            throw OperationException()
        }
    }
}