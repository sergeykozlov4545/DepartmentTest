package com.example.sergey.departmenttest.extansion

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.runInCoroutine(block: suspend () -> Unit) {
    try {
        launch { block() }
    } catch (exception: Exception) {
        throw exception
    }
}