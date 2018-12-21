package com.example.sergey.departmenttest.feature.core

import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.extansion.toast
import kotlinx.coroutines.CoroutineScope
import org.koin.standalone.KoinComponent
import java.io.IOException

open class BaseDialogFragment : DialogFragment(), BaseView, KoinComponent {

    override lateinit var scope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope = BaseCoroutineScope()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (scope as? BaseCoroutineScope)?.destroy()
    }

    override fun onError(exception: Exception) {
        val message = when {
            exception is IOException -> getString(R.string.connectionError)
            exception.message.isNullOrEmpty() -> getString(R.string.retryOperation)
            else -> exception.message
        }
        toast(message!!)
    }
}