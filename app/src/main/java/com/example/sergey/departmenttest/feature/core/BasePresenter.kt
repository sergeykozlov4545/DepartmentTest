package com.example.sergey.departmenttest.feature.core

interface BasePresenter<V : BaseView>

open class Presenter<V : BaseView>(v: V) : BasePresenter<V>