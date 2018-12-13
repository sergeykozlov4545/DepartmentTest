package com.example.sergey.departmenttest.feature.splash

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.sergey.departmenttest.feature.animation.AlphaAnimatorFactory
import com.example.sergey.departmenttest.feature.animation.RadiusAnimatorFactory

class PulsatileIndicator @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleRes: Int = 0
) : View(context, attrs, defStyleRes) {

    companion object {
        private const val MAX_RADIUS = 150f
        private const val DURATION_ANIMATION = 1000L
    }

    private val layers: List<IndicatorLayer> = createLayers()
    private val animators: List<ValueAnimator> = mutableListOf(createAlphaAnimator()).apply {
        addAll(createRadiusAnimators(layers))
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val maxRadiusValue = 2 * MAX_RADIUS.toInt()
        setMeasuredDimension(maxRadiusValue, maxRadiusValue)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val cx = width / 2f
        val cy = height / 2f

        layers.forEach { canvas?.drawCircle(cx, cy, it.currentRadius, it.paint) }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnimation()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimation()
    }

    private fun startAnimation() {
        animators.forEach { it.start() }
    }

    private fun stopAnimation() {
        animators.forEach { it.cancel() }
    }

    private fun createLayers(): List<IndicatorLayer> {
        val layers: MutableList<IndicatorLayer> = ArrayList()
        for (i in 0..0) {
            layers.add(
                    IndicatorLayer(
                            maxRadius = MAX_RADIUS * (100 - i * 30) / 100,
                            startDelay = i * 250L,
                            durationAnimation = DURATION_ANIMATION - i * 250L,
                            paint = Paint().apply { color = Color.RED }
                    )
            )
        }
        return layers
    }

    private fun createRadiusAnimators(layers: List<IndicatorLayer>): List<ValueAnimator> {
        return layers.map { createRadiusAnimator(it) }
    }

    private fun createAlphaAnimator() = AlphaAnimatorFactory.create(
            duration = DURATION_ANIMATION,
            updateListener = ValueAnimator.AnimatorUpdateListener {
                alpha = it.animatedValue as Float
            }
    )

    private fun createRadiusAnimator(layer: IndicatorLayer) = RadiusAnimatorFactory.create(
            maxRadius = layer.maxRadius,
            startDelay = layer.startDelay,
            duration = layer.durationAnimation,
            updateListener = ValueAnimator.AnimatorUpdateListener {
                layer.currentRadius = it.animatedValue as Float
                invalidate()
            }
    )

    private class IndicatorLayer(
            val maxRadius: Float,
            val paint: Paint,
            val startDelay: Long = 0L,
            val durationAnimation: Long = 1000L,
            var currentRadius: Float = 0f
    )
}

