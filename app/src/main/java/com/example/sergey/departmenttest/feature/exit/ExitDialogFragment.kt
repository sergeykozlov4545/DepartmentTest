package com.example.sergey.departmenttest.feature.exit

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import com.example.sergey.departmenttest.R

class ExitDialogFragment : DialogFragment() {
    companion object {
        private const val TAG = "exit_dialog"
        fun open(fragmentManager: FragmentManager) {
            if (fragmentManager.isStateSaved) {
                return
            }
            ExitDialogFragment().run {
                show(fragmentManager, TAG)
            }
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