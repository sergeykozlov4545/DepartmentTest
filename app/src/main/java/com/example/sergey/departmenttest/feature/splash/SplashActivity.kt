package com.example.sergey.departmenttest.feature.splash

import android.os.Bundle
import android.view.View
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.application.DepartmentsApplication
import com.example.sergey.departmenttest.domain.model.AuthorizedUser
import com.example.sergey.departmenttest.feature.core.BaseActivity
import com.example.sergey.departmenttest.feature.login.LoginActivity
import com.example.sergey.departmenttest.feature.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity(), SplashView {

    private val departmentsApplication by lazy { application as DepartmentsApplication }

    private val presenter by lazy { SplashPresenterImpl(this, departmentsApplication.authorizeInteractor) }

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
        progressBar.visibility = View.GONE
        presenter.loadAuthorizedUser()
    }

    override fun onGetAuthorizedUser(authorizedUser: AuthorizedUser?) =
            openLoginActivity(authorizedUser)

    override fun openLoginActivity(authorizedUser: AuthorizedUser?, errorMessage: String) {
        LoginActivity.start(this, authorizedUser, errorMessage)
        finish()
    }

    override fun openMainActivity() {
        MainActivity.start(this)
        finish()
    }
}