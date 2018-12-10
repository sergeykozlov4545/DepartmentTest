package com.example.sergey.departmenttest.feature.splash

import com.example.sergey.departmenttest.domain.model.AuthorizedUser
import com.example.sergey.departmenttest.feature.core.BaseView

interface SplashView : BaseView {
    fun openLoginActivity(authorizedUser: AuthorizedUser? = null)
    fun openMainActivity()
}