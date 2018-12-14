package com.example.sergey.departmenttest.feature.main

import com.example.sergey.departmenttest.domain.interactor.AuthorizeInteractor
import com.example.sergey.departmenttest.exception.OperationException
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter

interface MainPresenter : BasePresenter<MainView> {
    fun logout()
}

class MainPresenterImpl(
        override val view: MainView,
        private val authorizeInteractor: AuthorizeInteractor
) : Presenter<MainView>(view), MainPresenter {

    override fun logout() = runInCoroutine {
        val status = authorizeInteractor.logout()
        if (status.isSuccess) {
            view.openLoginActivity()
        } else {
            throw OperationException()
        }
    }
}