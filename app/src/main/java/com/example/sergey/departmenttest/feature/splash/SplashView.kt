package com.example.sergey.departmenttest.feature.splash

import com.example.sergey.departmenttest.data.model.AuthorizedUser
import com.example.sergey.departmenttest.feature.core.BaseView

interface SplashView : BaseView {
    fun openLoginActivity(user: AuthorizedUser? = null, errorMessage: String = "")
    fun openMainActivity()
}