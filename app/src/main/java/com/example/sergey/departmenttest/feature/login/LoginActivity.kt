package com.example.sergey.departmenttest.feature.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.domain.model.AuthorizedUser
import com.example.sergey.departmenttest.feature.core.BaseActivity

class LoginActivity : BaseActivity() {

    companion object {
        private const val BUNDLE_LOGIN = "login"
        private const val BUNDLE_PASSWORD = "password"

        fun start(context: Context, authorizedUser: AuthorizedUser? = null) {
            val intent = Intent(context, LoginActivity::class.java)
            if (authorizedUser != null) {
                intent.putExtra(BUNDLE_LOGIN, authorizedUser.login)
                intent.putExtra(BUNDLE_PASSWORD, authorizedUser.password)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
}