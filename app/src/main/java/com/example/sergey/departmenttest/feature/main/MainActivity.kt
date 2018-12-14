package com.example.sergey.departmenttest.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.application.DepartmentsApplication
import com.example.sergey.departmenttest.domain.model.Employee
import com.example.sergey.departmenttest.feature.core.BaseActivity
import com.example.sergey.departmenttest.feature.departmentList.DepartmentListFragment
import com.example.sergey.departmenttest.feature.employeeDetails.EmployeeDetailsActivity
import com.example.sergey.departmenttest.feature.login.LoginActivity

class MainActivity : BaseActivity(), MainView {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private val departmentsApplication by lazy { application as DepartmentsApplication }

    private val presenter by lazy { MainPresenterImpl(this, departmentsApplication.authorizeInteractor) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateTitle(getString(R.string.mainActivityTitle))
        setMenu(R.menu.activity_main) {
            if (it.itemId == R.id.menuLogout) {
                // TODO: Выход через диалог
                presenter.logout()
            }
            return@setMenu true
        }
    }

    override fun onResume() {
        super.onResume()
        DepartmentListFragment.open(supportFragmentManager, R.id.listContainer)
    }

    override fun openDetailsScreen(employee: Employee) {
        EmployeeDetailsActivity.start(this, employee)
    }

    override fun openLoginActivity() {
        LoginActivity.start(this)
        finish()
    }
}
