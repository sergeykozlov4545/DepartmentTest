package com.example.sergey.departmenttest.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.domain.model.Employee
import com.example.sergey.departmenttest.feature.core.BaseActivity
import com.example.sergey.departmenttest.feature.departmentList.DepartmentListFragment
import com.example.sergey.departmenttest.feature.employeeDetails.EmployeeDetailsActivity

class MainActivity : BaseActivity(), MainView {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        DepartmentListFragment.open(supportFragmentManager, R.id.listContainer)
    }

    override fun openDetailsScreen(employee: Employee) {
        EmployeeDetailsActivity.start(this, employee)
    }
}
