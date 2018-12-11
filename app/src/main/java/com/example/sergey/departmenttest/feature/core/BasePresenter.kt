package com.example.sergey.departmenttest.feature.core

import com.example.sergey.departmenttest.extansion.runInCoroutine
import kotlinx.coroutines.CoroutineScope

interface BasePresenter<V : BaseView>

open class Presenter<V : BaseView>(open val view: V) : BasePresenter<V> {
    fun runInCoroutine(block: suspend () -> Unit) {
        (view as? CoroutineScope)?.runInCoroutine(block)
    }
}