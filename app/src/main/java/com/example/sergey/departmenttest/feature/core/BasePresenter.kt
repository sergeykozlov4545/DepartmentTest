package com.example.sergey.departmenttest.feature.core

interface BasePresenter<V : BaseView>

open class Presenter<V : BaseView>(open val view: V) : BasePresenter<V>