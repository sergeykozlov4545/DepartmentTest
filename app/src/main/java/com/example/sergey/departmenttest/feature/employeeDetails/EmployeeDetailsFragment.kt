package com.example.sergey.departmenttest.feature.employeeDetails

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.application.DepartmentsApplication
import com.example.sergey.departmenttest.data.model.DownloadImage
import com.example.sergey.departmenttest.data.model.Employee
import com.example.sergey.departmenttest.extansion.hideView
import com.example.sergey.departmenttest.extansion.open
import com.example.sergey.departmenttest.extansion.showView
import com.example.sergey.departmenttest.extansion.toast
import com.example.sergey.departmenttest.feature.core.BaseFragment
import com.example.sergey.departmenttest.feature.toolbar.ToolbarCallback
import kotlinx.android.synthetic.main.fragment_employee_details.*

class EmployeeDetailsFragment : BaseFragment(), DetailsView {

    companion object {
        const val TAG = "employee_details_fragment"
        private const val BUNDLE_EMPLOYEE_ID = "employee_id"

        fun open(fragmentManager: FragmentManager, containerId: Int, employeeId: Long) =
                fragmentManager.open(containerId, getFragment(employeeId), TAG)

        private fun getFragment(employeeId: Long) = EmployeeDetailsFragment().apply {
            arguments = Bundle().apply {
                putLong(BUNDLE_EMPLOYEE_ID, employeeId)
            }
        }
    }

    private val departmentsApplication by lazy { activity!!.application as DepartmentsApplication }
    private val presenter by lazy { DetailsPresenterImpl(this, departmentsApplication.departmentsRepository) }

    private lateinit var employee: Employee

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_employee_details, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val employeeId = arguments?.getLong(BUNDLE_EMPLOYEE_ID, 0) ?: 0
        if (employeeId == 0L) {
            toast(getString(R.string.invalidEmployeeId))
            return
        }

        phoneView.setOnClickListener {
            if (employee.phone.isEmpty()) {
                toast(getString(R.string.phoneMissing))
                return@setOnClickListener
            }
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${employee.phone}"))
            openIntent(intent, R.string.chooseDialer, R.string.invalidChooseDialer)
        }
        emailView.setOnClickListener {
            if (employee.email.isEmpty()) {
                toast(getString(R.string.emailMissing))
                return@setOnClickListener
            }
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, employee.email)
            }
            openIntent(intent, R.string.chooseSender, R.string.invalidChooseSender)
        }

        progressBar.showView()
        presenter.loadEmployee(employeeId)
    }

    override fun onError(exception: Exception) {
        super.onError(exception)
        progressBar.hideView()
    }

    override fun onGetEmployee(employee: Employee, downloadImage: DownloadImage) {
        this.employee = employee

        (activity as? ToolbarCallback)?.updateTitle(employee.name)
        photoView.setImageBitmap(
                BitmapFactory.decodeByteArray(downloadImage.byteArray, 0, downloadImage.byteArray.size)
        )
        titleView.text = getString(R.string.employeeTitleFmt, employee.title)
        phoneView.text = getString(R.string.employeePhoneFmt, getPhoneValue(employee))
        emailView.text = getString(R.string.employeeEmailFmt, getEmailValue(employee))

        progressBar.hideView()
        contentGroup.showView()
    }

    private fun getPhoneValue(employee: Employee): String =
            if (employee.phone.isEmpty()) getString(R.string.missing) else employee.phone

    private fun getEmailValue(employee: Employee): String =
            if (employee.email.isEmpty()) getString(R.string.missing) else employee.email

    private fun openIntent(
            intent: Intent,
            @StringRes chooserTitleId: Int,
            @StringRes invalidChooserId: Int
    ) {
        try {
            startActivity(Intent.createChooser(intent, getString(chooserTitleId)))
        } catch (ignored: Exception) {
            toast(getString(invalidChooserId))
        }
    }
}