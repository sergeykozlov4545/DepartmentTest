package com.example.sergey.departmenttest.feature.employeeDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.domain.model.Employee
import com.example.sergey.departmenttest.extansion.toast
import com.example.sergey.departmenttest.feature.core.BaseActivity

class EmployeeDetailsActivity : BaseActivity() {
    companion object {
        private const val EXTRA_EMPLOYEE_ID = "employee_id"

        fun start(context: Context, employee: Employee) {
            context.startActivity(Intent(context, EmployeeDetailsActivity::class.java).apply {
                putExtra(EXTRA_EMPLOYEE_ID, employee.id)
            })
        }
    }

    private var employeeId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        employeeId = intent.getLongExtra(EXTRA_EMPLOYEE_ID, 0L)
        if (employeeId == 0L) {
            toast(getString(R.string.invalidEmployeeId))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        EmployeeDetailsFragment.open(supportFragmentManager, R.id.detailsContainer, employeeId)
    }
}