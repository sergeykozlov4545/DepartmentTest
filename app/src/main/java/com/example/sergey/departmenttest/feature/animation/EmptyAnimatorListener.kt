package com.example.sergey.departmenttest.feature.animation

import android.animation.Animator

open class EmptyAnimatorListener : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator?) {}

    override fun onAnimationEnd(animation: Animator?) {}

    override fun onAnimationCancel(animation: Animator?) {}

    override fun onAnimationRepeat(animation: Animator?) {}
}
