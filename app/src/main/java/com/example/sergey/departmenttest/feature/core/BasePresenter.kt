package com.example.sergey.departmenttest.feature.core

import kotlinx.coroutines.CoroutineScope

interface BasePresenter<V : BaseView>

open class Presenter<V : BaseView>(open val view: V) : BasePresenter<V>, CoroutineScope by view.scope