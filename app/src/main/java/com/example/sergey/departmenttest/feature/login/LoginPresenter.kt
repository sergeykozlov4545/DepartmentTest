package com.example.sergey.departmenttest.feature.login

import com.example.sergey.departmenttest.domain.interactor.AuthorizeInteractor
import com.example.sergey.departmenttest.exception.OperationException
import com.example.sergey.departmenttest.extansion.runInCoroutine
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter
import kotlinx.coroutines.CoroutineScope

interface LoginPresenter : BasePresenter<LoginView> {
    fun authorizeUser(login: String, password: String)
}

class LoginPresenterImpl(
        override val view: LoginView,
        private val authorizeInteractor: AuthorizeInteractor
) : Presenter<LoginView>(view), LoginPresenter {
    override fun authorizeUser(login: String, password: String) {
        val scope = view as? CoroutineScope ?: return

        scope.runInCoroutine {
            val status = authorizeInteractor.authorizeUser(login, password)
            if (status.isSuccess) {
                view.openMainActivity()
            } else {
                throw OperationException(status.message)
            }
        }
    }
}