package com.example.sergey.departmenttest.extansion

import android.animation.Animator
import android.animation.ValueAnimator
import com.example.sergey.departmenttest.feature.animation.EmptyAnimatorListener

fun ValueAnimator.addRestartedListener(listener: () -> Unit) {
    addListener(object : EmptyAnimatorListener() {

        private var isCanceled = false

        override fun onAnimationStart(animation: Animator?) {
            isCanceled = false
        }

        override fun onAnimationEnd(animation: Animator?) {
            animation?.isRunning
            if (!isCanceled) {
                listener()
            }
        }

        override fun onAnimationCancel(animation: Animator?) {
            isCanceled = true
        }
    })
}