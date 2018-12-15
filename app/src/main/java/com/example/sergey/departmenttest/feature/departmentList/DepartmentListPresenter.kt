package com.example.sergey.departmenttest.feature.departmentList

import com.example.sergey.departmenttest.data.repository.DepartmentsRepository
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter

interface DepartmentListPresenter : BasePresenter<DepartmentListView> {
    fun loadDepartmentsInfo()
}

class DepartmentListPresenterImpl(
        override val view: DepartmentListView,
        private val departmentsRepository: DepartmentsRepository
) : Presenter<DepartmentListView>(view), DepartmentListPresenter {

    override fun loadDepartmentsInfo() = runInCoroutine {
        val elements = departmentsRepository.getDepartmentsInfo()
        view.onGetTreeElements(elements)
    }
}