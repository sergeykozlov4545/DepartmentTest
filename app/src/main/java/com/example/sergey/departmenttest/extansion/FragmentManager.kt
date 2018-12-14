package com.example.sergey.departmenttest.extansion

import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

fun FragmentManager.open(containerId: Int, fragment: Fragment, tag: String) {
    if (!isStateSaved) {
        beginTransaction().replace(containerId, fragment, tag).commitNow()
    }
}

fun FragmentManager.close(tag: String) {
    if (!isStateSaved) {
        val fragment = findFragmentByTag(tag) ?: return
        beginTransaction().remove(fragment).commitNow()
    }
}

fun FragmentManager.openDialog(tag: String, getFragment: () -> DialogFragment) {
    if (!isStateSaved) {
        getFragment().show(this, tag)
    }
}