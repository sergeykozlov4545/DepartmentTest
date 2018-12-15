package com.example.sergey.departmenttest.feature.splash

import com.example.sergey.departmenttest.data.model.AuthorizedUser
import com.example.sergey.departmenttest.feature.core.BaseView

interface SplashView : BaseView {
    fun onGetAuthorizedUser(authorizedUser: AuthorizedUser? = null)
    fun openLoginActivity(authorizedUser: AuthorizedUser? = null, errorMessage: String = "")
    fun openMainActivity()
}