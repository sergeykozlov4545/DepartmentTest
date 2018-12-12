package com.example.sergey.departmenttest.feature.employeeDetails

import com.example.sergey.departmenttest.domain.interactor.DepartmentsInteractor
import com.example.sergey.departmenttest.exception.OperationException
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter

interface DetailsPresenter : BasePresenter<DetailsView> {
    fun loadEmployee(id: Long)
}

class DetailsPresenterImpl(
        override val view: DetailsView,
        private val departmentsInteractor: DepartmentsInteractor
) : Presenter<DetailsView>(view), DetailsPresenter {

    override fun loadEmployee(id: Long) = runInCoroutine {
        val employee = departmentsInteractor.getEmployee(id)
        val downloadImage = departmentsInteractor.getEmployeePhoto(id)

        if (employee == null || downloadImage == null) {
            throw OperationException()
        }
        view.onGetEmployee(employee, downloadImage)
    }
}