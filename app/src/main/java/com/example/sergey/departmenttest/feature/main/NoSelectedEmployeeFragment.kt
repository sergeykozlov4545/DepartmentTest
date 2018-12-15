package com.example.sergey.departmenttest.feature.main

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.extansion.open
import com.example.sergey.departmenttest.feature.core.BaseFragment

class NoSelectedEmployeeFragment : BaseFragment() {

    companion object {
        const val TAG = "no_selected_employee_fragment"

        fun open(fragmentManager: FragmentManager, containerId: Int) =
                fragmentManager.open(containerId, getFragment(fragmentManager), TAG)

        private fun getFragment(fragmentManager: FragmentManager) =
                fragmentManager.findFragmentByTag(TAG) ?: NoSelectedEmployeeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return TextView(activity).apply {
            layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.CENTER
            setBackgroundColor(Color.WHITE)
            text = getString(R.string.changeEmployee)
            textSize = 16f
            setTextColor(Color.BLACK)
        }
    }
}