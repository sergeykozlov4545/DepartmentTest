package com.example.sergey.departmenttest.feature.animation

import android.animation.ValueAnimator
import android.view.animation.Animation


object AnimatorFactory {
    fun create(
            startValue: Float,
            endValue: Float,
            startDelay: Long = 0L,
            duration: Long = 1000L,
            updateListener: ValueAnimator.AnimatorUpdateListener? = null
    ): ValueAnimator {
        return ValueAnimator.ofFloat(startValue, endValue).apply {
            this.startDelay = startDelay
            this.duration = duration
            this.repeatCount = Animation.INFINITE
            addUpdateListener(updateListener)
        }
    }
}

object AlphaAnimatorFactory {
    fun create(
            startDelay: Long = 0L,
            duration: Long = 1000L,
            updateListener: ValueAnimator.AnimatorUpdateListener
    ): ValueAnimator {
        return AnimatorFactory.create(
                startValue = 1f,
                endValue = 0f,
                startDelay = startDelay,
                duration = duration,
                updateListener = updateListener
        )
    }
}

object RadiusAnimatorFactory {
    fun create(
            maxRadius: Float,
            startDelay: Long = 0L,
            duration: Long = 1000L,
            updateListener: ValueAnimator.AnimatorUpdateListener
    ): ValueAnimator {
        return AnimatorFactory.create(
                startValue = 0f,
                endValue = maxRadius,
                startDelay = startDelay,
                duration = duration,
                updateListener = updateListener
        )
    }
}