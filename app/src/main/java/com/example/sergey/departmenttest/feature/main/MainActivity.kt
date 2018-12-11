package com.example.sergey.departmenttest.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.application.DepartmentsApplication
import com.example.sergey.departmenttest.domain.model.Employee
import com.example.sergey.departmenttest.domain.model.TreeElement
import com.example.sergey.departmenttest.feature.core.BaseActivity

class MainActivity : BaseActivity(), DepartmentListView {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private val departmentsApplication by lazy { application as DepartmentsApplication }

    private val presenter by lazy { DepartmentListPresenterImpl(this, departmentsApplication.departmentsInteractor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadTreeElements()
    }

    override fun onGetTreeElements(elements: List<TreeElement>) {
        // TODO: Обновить адаптер
    }

    override fun openDetalisationEmployee(employee: Employee) {
        // TODO: Открыть детализацию
    }
}
