package com.example.sergey.departmenttest.feature.core

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.extansion.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.io.IOException
import kotlin.coroutines.CoroutineContext

open class BaseFragment : Fragment(), CoroutineScope, BaseView {

    private lateinit var job: Job
    private var isCreatedJob = false

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        job = Job()
        isCreatedJob = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
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
}