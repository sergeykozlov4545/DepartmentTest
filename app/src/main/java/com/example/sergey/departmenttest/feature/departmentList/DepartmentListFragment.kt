package com.example.sergey.departmenttest.feature.departmentList

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.application.DepartmentsApplication
import com.example.sergey.departmenttest.data.model.Employee
import com.example.sergey.departmenttest.data.model.TreeElement
import com.example.sergey.departmenttest.extansion.open
import com.example.sergey.departmenttest.feature.core.BaseFragment
import com.example.sergey.departmenttest.feature.main.MainView
import com.example.sergey.departmenttest.feature.treeView.ItemAdapter
import kotlinx.android.synthetic.main.fragment_department_list.*

class DepartmentListFragment : BaseFragment(), DepartmentListView {

    companion object {
        const val TAG = "department_list_fragment"

        fun open(fragmentManager: FragmentManager, containerId: Int) {
            fragmentManager.open(containerId, getFragment(fragmentManager), TAG)
        }

        private fun getFragment(fragmentManager: FragmentManager) =
                fragmentManager.findFragmentByTag(TAG) ?: DepartmentListFragment()
    }

    private val departmentsApplication by lazy { activity!!.application as DepartmentsApplication }
    private val presenter by lazy { DepartmentListPresenterImpl(this, departmentsApplication.departmentsRepository) }

    private val adapter = ItemAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_department_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(treeListView) {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = this@DepartmentListFragment.adapter.apply {
                itemClick = {
                    // TODO: Обработать тут клик
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadDepartmentsInfo()
    }

    override fun onError(exception: Exception) {
        super.onError(exception)
        (activity as? MainView)?.onContentLoaded()
    }

    override fun onGetTreeElements(elements: List<TreeElement>) {
        (activity as? MainView)?.onContentLoaded()
        adapter.data = elements
        treeListView.visibility = View.VISIBLE
    }

    override fun openDetailsScreen(employee: Employee) {
        (activity as? MainView)?.openDetailsScreen(employee)
    }
}