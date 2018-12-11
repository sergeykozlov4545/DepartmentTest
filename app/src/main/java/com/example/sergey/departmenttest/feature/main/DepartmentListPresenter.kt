package com.example.sergey.departmenttest.feature.main

import com.example.sergey.departmenttest.domain.interactor.DepartmentsInteractor
import com.example.sergey.departmenttest.exception.OperationException
import com.example.sergey.departmenttest.extansion.runInCoroutine
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter
import kotlinx.coroutines.CoroutineScope

interface DepartmentListPresenter : BasePresenter<DepartmentListView> {
    fun loadDepartments()
}

class DepartmentListPresenterImpl(
        override val view: DepartmentListView,
        private val departmentsInteractor: DepartmentsInteractor
) : Presenter<DepartmentListView>(view), DepartmentListPresenter {

    override fun loadDepartments() {
        view.takeIf { it is CoroutineScope }
                ?.also { (it as CoroutineScope).runInCoroutine(this::loadDepartmentsAsync) }
    }

    private suspend fun loadDepartmentsAsync() {
        departmentsInteractor.getDepartments().takeIf { it != null }
                ?.run { view.onGetDepartments(this) } ?: throw OperationException()
    }
}