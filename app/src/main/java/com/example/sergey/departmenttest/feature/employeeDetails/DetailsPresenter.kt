package com.example.sergey.departmenttest.feature.employeeDetails

import com.example.sergey.departmenttest.domain.interactor.DepartmentsInteractor
import com.example.sergey.departmenttest.exception.OperationException
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter

interface DetailsPresenter : BasePresenter<DetailsView> {
    fun loadEmployee(id: Long)
    fun loadEmployeePhoto(id: Long)
}

class DetailsPresenterImpl(
        override val view: DetailsView,
        private val departmentsInteractor: DepartmentsInteractor
) : Presenter<DetailsView>(view), DetailsPresenter {

    override fun loadEmployee(id: Long) = runInCoroutine {
        val employee = departmentsInteractor.getEmployee(id)
        employee.takeIf { it != null }
                ?.run { view.onGetEmployee(this) } ?: throw OperationException()
    }

    override fun loadEmployeePhoto(id: Long) = runInCoroutine {
        val downloadImage = departmentsInteractor.getEmployeePhoto(id)
        downloadImage.takeIf { it != null }
                ?.run { view.onGetEmployeePhoto(this) }
    }
}