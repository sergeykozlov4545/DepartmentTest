package com.example.sergey.departmenttest.feature.core

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.extansion.toast
import com.example.sergey.departmenttest.feature.toolbar.ToolbarCallback
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.io.IOException
import kotlin.coroutines.CoroutineContext

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), CoroutineScope, BaseView, ToolbarCallback {

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
        val message = when {
            exception is IOException -> getString(R.string.connectionError)
            exception.message.isNullOrEmpty() -> getString(R.string.retryOperation)
            else -> exception.message
        }
        toast(message!!)
    }

    override fun setBackAction(drawableId: Int, action: () -> Unit) {
        toolbarView.navigationIcon = ContextCompat.getDrawable(this, drawableId)
        toolbarView.setNavigationOnClickListener { action() }
    }

    override fun updateTitle(title: String) {
        titleView.text = title
    }

    override fun setMenu(menuResId: Int, clickListener: (item: MenuItem) -> Boolean) {
        with(toolbarView) {
            inflateMenu(menuResId)
            setOnMenuItemClickListener {
                return@setOnMenuItemClickListener clickListener(it)
            }
        }
    }
}