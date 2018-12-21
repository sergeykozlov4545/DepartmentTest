package com.example.sergey.departmenttest.feature.logout

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.extansion.openDialog
import com.example.sergey.departmenttest.feature.core.BaseDialogFragment
import com.example.sergey.departmenttest.feature.main.MainView
import org.koin.core.parameter.parametersOf
import org.koin.standalone.inject

class LogoutDialog : BaseDialogFragment(), LogoutView {

    companion object {
        private const val TAG = "logout_dialog"

        fun open(fragmentManager: FragmentManager) {
            fragmentManager.openDialog(TAG) { LogoutDialog() }
        }
    }

    private val presenter: LogoutPresenter by inject { parametersOf(this) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setTitle(R.string.logoutDialogTitle)
                .setMessage(R.string.logoutDialogMessage)
                .setPositiveButton(R.string.logoutDialogPositive) { _, _ -> presenter.logout() }
                .setNegativeButton(R.string.logoutDialogNegative) { _, _ -> }
                .create()
    }

    override fun openLoginActivity() = (activity as? MainView)?.openLoginActivity() ?: Unit
}