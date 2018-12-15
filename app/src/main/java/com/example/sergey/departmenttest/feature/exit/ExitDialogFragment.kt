package com.example.sergey.departmenttest.feature.exit

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.extansion.openDialog
import com.example.sergey.departmenttest.feature.core.BaseDialogFragment

class ExitDialogFragment : BaseDialogFragment() {
    companion object {
        private const val TAG = "exit_dialog"

        fun open(fragmentManager: FragmentManager) {
            fragmentManager.openDialog(TAG) { ExitDialogFragment() }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setTitle(R.string.exitDialogTitle)
                .setMessage(R.string.exitDialogMessage)
                .setPositiveButton(R.string.exitDialogPositive) { _, _ -> activity?.finish() }
                .setNegativeButton(R.string.exitDialogNegative) { _, _ -> }
                .create()
    }
}