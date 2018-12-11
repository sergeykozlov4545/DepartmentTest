package com.example.sergey.departmenttest.feature.main

import com.example.sergey.departmenttest.domain.interactor.DepartmentsInteractor
import com.example.sergey.departmenttest.exception.OperationException
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter

interface DepartmentPresenter : BasePresenter<DepartmentView> {
    fun loadEmployee(id: Long)
}

class DepartmentPresenterImpl(
        override val view: DepartmentView,
        private val departmentsInteractor: DepartmentsInteractor
) : Presenter<DepartmentView>(view), DepartmentPresenter {

    override fun loadEmployee(id: Long) = runInCoroutine {
        val employee = departmentsInteractor.getEmployee(id)
        employee.takeIf { it != null }
                ?.run { view.onGetEmployee(this) } ?: throw OperationException()
    }
}