package com.example.sergey.departmenttest.feature.departmentList

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.data.model.DepartmentElement
import com.example.sergey.departmenttest.data.model.EmployeeElement
import com.example.sergey.departmenttest.data.model.TreeElement
import com.example.sergey.departmenttest.extansion.between
import com.example.sergey.departmenttest.extansion.open
import com.example.sergey.departmenttest.extansion.showView
import com.example.sergey.departmenttest.feature.core.BaseFragment
import com.example.sergey.departmenttest.feature.main.MainView
import com.example.sergey.departmenttest.feature.treeView.ItemAdapter
import kotlinx.android.synthetic.main.fragment_department_list.*
import org.koin.core.parameter.parametersOf
import org.koin.standalone.inject

class DepartmentListFragment : BaseFragment(), DepartmentListView {

    companion object {
        const val TAG = "department_list_fragment"

        fun open(fragmentManager: FragmentManager, containerId: Int) {
            fragmentManager.open(containerId, getFragment(fragmentManager), TAG)
        }

        private fun getFragment(fragmentManager: FragmentManager) =
                fragmentManager.findFragmentByTag(TAG) ?: DepartmentListFragment()
    }

    private val presenter: DepartmentListPresenter by inject { parametersOf(this) }

    private val info: MutableList<TreeElement> = ArrayList()
    private val adapter = ItemAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_department_list, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(treeListView) {
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = this@DepartmentListFragment.adapter.apply {
                itemClick = ::itemClick
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
        info.addAll(elements.apply { first().isVisible = true })
        adapter.data = info.filter(TreeElement::isVisible)
        treeListView.showView()
    }

    private fun itemClick(treeElement: TreeElement) {
        when (treeElement) {
            is EmployeeElement -> (activity as? MainView)?.openDetailsScreen(treeElement.employee)
            is DepartmentElement -> toggleDepartmentElement(treeElement)
        }
    }

    private fun toggleDepartmentElement(element: DepartmentElement) {
        if (element.isOpened) {
            info.filter { it.timeRange.between(element.timeRange) }
                    .forEach {
                        it.isVisible = false
                        if (it is DepartmentElement) {
                            it.isOpened = false
                        }
                    }
            element.isVisible = true
        } else {
            info.filter { it.parentId == element.id }.forEach { it.isVisible = true }
            element.isOpened = true
        }
        adapter.data = info.filter(TreeElement::isVisible)
    }
}