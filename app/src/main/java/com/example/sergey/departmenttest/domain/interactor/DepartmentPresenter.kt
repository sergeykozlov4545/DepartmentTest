package com.example.sergey.departmenttest.domain.interactor

import com.example.sergey.departmenttest.exception.OperationException
import com.example.sergey.departmenttest.extansion.runInCoroutine
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter
import kotlinx.coroutines.CoroutineScope

interface DepartmentPresenter : BasePresenter<DepartmentView> {
    fun loadEmployee(id: Long)
}

class DepartmentPresenterImpl(
        override val view: DepartmentView,
        private val departmentsInteractor: DepartmentsInteractor
) : Presenter<DepartmentView>(view), DepartmentPresenter {

    override fun loadEmployee(id: Long) {
        view.takeIf { it is CoroutineScope }
                ?.also { (it as CoroutineScope).runInCoroutine {loadEmployeeAsync(id)} }
    }

    private suspend fun loadEmployeeAsync(id: Long) {
        departmentsInteractor.getEmployee(id).takeIf { it != null }
                ?.run { view.onGetEmployee(this) } ?: throw OperationException()
    }
}