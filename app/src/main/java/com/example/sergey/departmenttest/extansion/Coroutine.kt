package com.example.sergey.departmenttest.extansion

import com.example.sergey.departmenttest.feature.core.ErrorView
import com.example.sergey.departmenttest.feature.core.Presenter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.runInCoroutine(block: suspend () -> Unit) {
    launch {
        try {
            block()
        } catch (exception: Exception) {
            if (exception is CancellationException) {
                throw exception
            }

            this@runInCoroutine.takeIf { it is ErrorView }
                    ?.also { (it as? ErrorView)?.onError(exception) }
                    ?: this@runInCoroutine.takeIf { it is Presenter<*> }
                            ?.also { (it as? Presenter<*>)?.view?.onError(exception) }
                    ?: throw exception
        }
    }
}