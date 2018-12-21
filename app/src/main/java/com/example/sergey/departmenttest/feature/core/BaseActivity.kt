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
import org.koin.standalone.KoinComponent
import java.io.IOException

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), BaseView, ToolbarCallback, KoinComponent {

    override lateinit var scope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope = BaseCoroutineScope()
    }

    override fun onDestroy() {
        super.onDestroy()
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
            setOnMenuItemClickListener(clickListener)
        }
    }
}