package com.example.sergey.departmenttest.feature.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.application.DepartmentsApplication
import com.example.sergey.departmenttest.domain.model.AuthorizedUser
import com.example.sergey.departmenttest.exception.OperationException
import com.example.sergey.departmenttest.extansion.toast
import com.example.sergey.departmenttest.feature.core.BaseActivity
import com.example.sergey.departmenttest.feature.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), LoginView {

    companion object {
        private const val BUNDLE_LOGIN = "login"
        private const val BUNDLE_PASSWORD = "password"
        private const val BUNDLE_ERROR_MESSAGE = "error_message"

        fun start(context: Context, authorizedUser: AuthorizedUser? = null, errorMessage: String = "") {
            val intent = Intent(context, LoginActivity::class.java)
            if (authorizedUser != null) {
                intent.putExtra(BUNDLE_LOGIN, authorizedUser.login)
                intent.putExtra(BUNDLE_PASSWORD, authorizedUser.password)
            }
            intent.putExtra(BUNDLE_ERROR_MESSAGE, errorMessage)
            context.startActivity(intent)
        }
    }

    private val departmentsApplication by lazy { application as DepartmentsApplication }

    private val presenter by lazy { LoginPresenterImpl(this, departmentsApplication.authorizeInteractor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        intent.getStringExtra(BUNDLE_ERROR_MESSAGE).takeIf { !it.isNullOrEmpty() }
                ?.run { toast(this) }

        sigInView.setOnClickListener {
            val login = loginView.text.toString().trim()
            val password = passwordView.text.toString().trim()

            if (login.isEmpty() || password.isEmpty()) {
                onError(OperationException(getString(R.string.enterLoginEndPassword)))
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE
            presenter.authorizeUser(login, password)
        }
    }

    override fun onError(exception: Exception) {
        super.onError(exception)
        progressBar.visibility = View.INVISIBLE
    }

    override fun openMainActivity() {
        MainActivity.start(this)
        finish()
    }
}