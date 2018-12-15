package com.example.sergey.departmenttest.feature.logout

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.application.DepartmentsApplication
import com.example.sergey.departmenttest.extansion.openDialog
import com.example.sergey.departmenttest.extansion.toast
import com.example.sergey.departmenttest.feature.main.MainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class LogoutDialog : DialogFragment(), LogoutView, CoroutineScope {

    companion object {
        private const val TAG = "logout_dialog"

        fun open(fragmentManager: FragmentManager) {
            fragmentManager.openDialog(TAG) { LogoutDialog() }
        }
    }

    private val departmentsApplication by lazy { activity!!.application as DepartmentsApplication }

    private val presenter by lazy {
        LogoutPresenterImpl(this, departmentsApplication.authRepository, departmentsApplication.departmentsRepository)
    }

    private lateinit var job: Job
    private var isCreatedJob = false

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        isCreatedJob = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setTitle(R.string.logoutDialogTitle)
                .setMessage(R.string.logoutDialogMessage)
                .setPositiveButton(R.string.logoutDialogPositive) { _, _ -> presenter.logout() }
                .setNegativeButton(R.string.logoutDialogNegative) { _, _ -> }
                .create()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isCreatedJob) job.cancel()
    }

    override fun onError(exception: Exception) {
        val message = when {
            exception is IOException -> getString(R.string.connectionError)
            exception.message.isNullOrEmpty() -> getString(R.string.retryOperation)
            else -> exception.message
        }
        toast(message!!)
    }

    override fun openLoginActivity() {
        (activity as? MainView)?.openLoginActivity()
    }
}