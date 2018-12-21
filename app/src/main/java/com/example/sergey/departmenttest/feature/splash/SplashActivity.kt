package com.example.sergey.departmenttest.feature.splash

import android.os.Bundle
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.data.model.AuthorizedUser
import com.example.sergey.departmenttest.feature.core.BaseActivity
import com.example.sergey.departmenttest.feature.login.LoginActivity
import com.example.sergey.departmenttest.feature.main.MainActivity
import org.koin.core.parameter.parametersOf
import org.koin.standalone.inject

class SplashActivity : BaseActivity(), SplashView {

    private val presenter: SplashPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        presenter.checkAuthorizedUser()
    }

    override fun onError(exception: Exception) {
        super.onError(exception)
        presenter.requestOpenLoginActivity()
    }

    override fun openLoginActivity(user: AuthorizedUser?, errorMessage: String) {
        LoginActivity.start(this, user, errorMessage)
        finish()
    }

    override fun openMainActivity() {
        MainActivity.start(this)
        finish()
    }
}