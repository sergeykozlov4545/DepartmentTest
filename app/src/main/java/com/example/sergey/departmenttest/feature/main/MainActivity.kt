package com.example.sergey.departmenttest.feature.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.domain.model.Employee
import com.example.sergey.departmenttest.extansion.isTablet
import com.example.sergey.departmenttest.extansion.toast
import com.example.sergey.departmenttest.feature.core.BaseActivity
import com.example.sergey.departmenttest.feature.departmentList.DepartmentListFragment
import com.example.sergey.departmenttest.feature.employeeDetails.EmployeeDetailsActivity
import com.example.sergey.departmenttest.feature.employeeDetails.EmployeeDetailsFragment
import com.example.sergey.departmenttest.feature.exit.ExitDialogFragment
import com.example.sergey.departmenttest.feature.login.LoginActivity
import com.example.sergey.departmenttest.feature.logout.LogoutDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {

    companion object {
        private const val BUNDLE_SELECTED_EMPLOYEE_ID = "selected_employee_id"

        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private var selectedEmployeeId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updateTitle(getString(R.string.mainActivityTitle))
        setMenu(R.menu.activity_main) {
            if (it.itemId == R.id.menuLogout) {
                LogoutDialog.open(supportFragmentManager)
            }
            return@setMenu true
        }

        selectedEmployeeId =
                savedInstanceState?.getLong(BUNDLE_SELECTED_EMPLOYEE_ID, 0) ?: 0
    }

    override fun onResume() {
        super.onResume()
        progressBar.visibility = View.VISIBLE
        DepartmentListFragment.open(supportFragmentManager, R.id.listContainer)

        if (!isTablet()) {
            return
        }
        if (isValidEmployeeId(selectedEmployeeId)) {
            openEmployeeDetailsFragment(selectedEmployeeId)
            return
        }
        openNoSelectedEmployeeFragment()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.run {
            putLong(BUNDLE_SELECTED_EMPLOYEE_ID, selectedEmployeeId)
        }
    }

    override fun onBackPressed() {
        if (isValidEmployeeId(selectedEmployeeId)) {
            updateTitle(getString(R.string.mainActivityTitle))
            selectedEmployeeId = 0
            openNoSelectedEmployeeFragment()
            return
        }
        ExitDialogFragment.open(supportFragmentManager)
    }

    override fun openDetailsScreen(employee: Employee) {
        if (isNotValidEmployeeId(employee.id)) {
            toast(getString(R.string.invalidEmployeeId))
            return
        }
        if (selectedEmployeeId == employee.id) {
            return
        }
        if (isTablet()) {
            selectedEmployeeId = employee.id
            openEmployeeDetailsFragment(selectedEmployeeId)
        } else {
            EmployeeDetailsActivity.start(this, employee)
        }
    }

    override fun openLoginActivity() {
        LoginActivity.start(this)
        finish()
    }

    override fun onContentLoaded() {
        progressBar.visibility = View.GONE
    }

    private fun openEmployeeDetailsFragment(employeeId: Long) {
        EmployeeDetailsFragment.open(supportFragmentManager, R.id.detailsContainer, employeeId)
    }

    private fun openNoSelectedEmployeeFragment() {
        NoSelectedEmployeeFragment.open(supportFragmentManager, R.id.detailsContainer)
    }

    private fun isValidEmployeeId(id: Long) = id != 0L

    private fun isNotValidEmployeeId(id: Long) = id == 0L
}
