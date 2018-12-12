package com.example.sergey.departmenttest.feature.departmentList

import com.example.sergey.departmenttest.domain.interactor.DepartmentsInteractor
import com.example.sergey.departmenttest.domain.model.DepartmentElement
import com.example.sergey.departmenttest.domain.model.EmployeeElement
import com.example.sergey.departmenttest.domain.model.TreeElement
import com.example.sergey.departmenttest.feature.core.BasePresenter
import com.example.sergey.departmenttest.feature.core.Presenter

interface DepartmentListPresenter : BasePresenter<DepartmentListView> {
    fun loadTreeElements()
    fun treeElementClicked(treeElement: TreeElement)
}

class DepartmentListPresenterImpl(
        override val view: DepartmentListView,
        private val departmentsInteractor: DepartmentsInteractor
) : Presenter<DepartmentListView>(view), DepartmentListPresenter {

    override fun loadTreeElements() = runInCoroutine {
        val elements = departmentsInteractor.getTreeElements()
        view.onGetTreeElements(elements)
    }

    override fun treeElementClicked(treeElement: TreeElement) = runInCoroutine {
        when (treeElement) {
            is EmployeeElement -> view.openDetailsScreen(treeElement.employee)
            is DepartmentElement -> {
                val elements = departmentsInteractor.toggleDepartmentElement(treeElement)
                view.onGetTreeElements(elements)
            }
        }
    }
}