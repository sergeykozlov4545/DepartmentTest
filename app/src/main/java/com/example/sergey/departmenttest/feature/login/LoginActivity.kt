package com.example.sergey.departmenttest.feature.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.data.model.AuthorizedUser
import com.example.sergey.departmenttest.exception.OperationException
import com.example.sergey.departmenttest.extansion.hideView
import com.example.sergey.departmenttest.extansion.showView
import com.example.sergey.departmenttest.extansion.toast
import com.example.sergey.departmenttest.feature.core.BaseActivity
import com.example.sergey.departmenttest.feature.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.core.parameter.parametersOf
import org.koin.standalone.inject

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

            intent.runCatching { }
        }
    }

    private val presenter: LoginPresenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        updateTitle(getString(R.string.appName))

        intent.getStringExtra(BUNDLE_ERROR_MESSAGE).takeIf { !it.isNullOrEmpty() }
                ?.run { toast(this) }

        intent.getStringExtra(BUNDLE_LOGIN).takeIf { !it.isNullOrEmpty() }?.run {
            loginView.setText(this)
        }
        intent.getStringExtra(BUNDLE_PASSWORD).takeIf { !it.isNullOrEmpty() }?.run {
            passwordView.setText(this)
        }

        // TODO: Убрать в конце
        loginView.setText("test_user")
        passwordView.setText("test_pass")

        sigInView.setOnClickListener {
            val login = loginView.text.toString().trim()
            val password = passwordView.text.toString().trim()

            if (login.isEmpty() || password.isEmpty()) {
                onError(OperationException(getString(R.string.enterLoginEndPassword)))
                return@setOnClickListener
            }

            progressBar.showView()
            presenter.authorizeUser(login, password)
        }
    }

    override fun onError(exception: Exception) {
        super.onError(exception)
        progressBar.hideView(true)
    }

    override fun openMainActivity() {
        progressBar.hideView(true)
        MainActivity.start(this)
        finish()
    }
}