package com.example.sergey.departmenttest.feature.core

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.extansion.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

@SuppressLint("Registered")
class BaseActivity : AppCompatActivity(), CoroutineScope, ErrorView {

    private lateinit var job: Job
    private var isCreatedJob = false

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        isCreatedJob = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isCreatedJob) job.cancel()
    }

    override fun onError(exception: Exception) {
        val message = if (exception.message.isNullOrEmpty()) {
            getString(R.string.retryOperation)
        } else {
            exception.message
        }
        toast(message!!)
    }
}