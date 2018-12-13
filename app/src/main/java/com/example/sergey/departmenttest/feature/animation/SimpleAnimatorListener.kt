package com.example.sergey.departmenttest.feature.animation

import android.animation.Animator
import android.util.Log

open class SimpleAnimatorListener : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator?) {
//        Log.e("TAG", "onAnimationStart(): $animation")
    }

    override fun onAnimationEnd(animation: Animator?) {
//        Log.e("TAG", "onAnimationEnd(): $animation")
    }

    override fun onAnimationCancel(animation: Animator?) {
//        Log.e("TAG", "onAnimationCancel(): $animation")
    }

    override fun onAnimationRepeat(animation: Animator?) {
//        Log.e("TAG", "onAnimationRepeat(): $animation")
    }
}
