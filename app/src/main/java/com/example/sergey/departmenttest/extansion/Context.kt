package com.example.sergey.departmenttest.extansion

import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
fun Context.longToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Fragment.toast(message: String) {
    activity.takeIf { it != null }
            ?.run { Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }
}
fun Fragment.longToast(message: String) {
    activity.takeIf { it != null }
            ?.run { Toast.makeText(this, message, Toast.LENGTH_LONG).show() }
}
