package com.example.sergey.departmenttest.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.application.DepartmentsApplication
import com.example.sergey.departmenttest.domain.model.Employee
import com.example.sergey.departmenttest.domain.model.TreeElement
import com.example.sergey.departmenttest.feature.core.BaseActivity
import com.example.sergey.departmenttest.feature.main.treeView.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), DepartmentListView {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private val departmentsApplication by lazy { application as DepartmentsApplication }

    private val presenter by lazy { DepartmentListPresenterImpl(this, departmentsApplication.departmentsInteractor) }

    private val adapter = ItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(treeListView) {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = this@MainActivity.adapter.apply {
                itemClick = {
                    presenter.treeElementClicked(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        progressBar.visibility = View.VISIBLE
        presenter.loadTreeElements()
    }

    override fun onError(exception: Exception) {
        super.onError(exception)
        progressBar.visibility = View.GONE
    }

    override fun onGetTreeElements(elements: List<TreeElement>) {
        progressBar.visibility = View.GONE
        adapter.data = elements
        treeListView.visibility = View.VISIBLE
    }

    override fun openDetalisationEmployee(employee: Employee) {
        // TODO: Открыть детализацию
    }
}
