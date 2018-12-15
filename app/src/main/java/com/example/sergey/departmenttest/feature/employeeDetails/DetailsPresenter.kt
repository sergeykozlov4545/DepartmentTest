package com.example.sergey.departmenttest.feature.employeeDetails

import com.example.sergey.departmenttest.data.repository.DepartmentsRepository
import com.example.sergey.departmenttest.exception.OperationException
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter

interface DetailsPresenter : BasePresenter<DetailsView> {
    fun loadEmployee(id: Long)
}

class DetailsPresenterImpl(
        override val view: DetailsView,
        private val departmentsRepository: DepartmentsRepository
) : Presenter<DetailsView>(view), DetailsPresenter {

    override fun loadEmployee(id: Long) = runInCoroutine {
        val employee = departmentsRepository.getEmployee(id) ?: throw OperationException()
        val downloadImage = departmentsRepository.getEmployeePhoto(id)
        view.onGetEmployee(employee, downloadImage)
    }
}