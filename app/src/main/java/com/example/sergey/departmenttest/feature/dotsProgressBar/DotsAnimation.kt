package com.example.sergey.departmenttest.feature.dotsProgressBar

import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation

interface AnimationCallback {
    fun onAnimationRepeat()
    fun applyTransformation()
}

class DotsAnimation(
        animationDuration: Int,
        private var animationCallback: AnimationCallback? = null
) : Animation() {

    init {
        duration = animationDuration.toLong()
        repeatCount = Animation.INFINITE
        interpolator = LinearInterpolator()
        setAnimationListener(object : AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
                animationCallback?.onAnimationRepeat()
            }

            override fun onAnimationEnd(animation: Animation?) {}

            override fun onAnimationStart(animation: Animation?) {}

        })
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        super.applyTransformation(interpolatedTime, t)
        animationCallback?.applyTransformation()
    }
    fun detach() {
        animationCallback = null
    }
}