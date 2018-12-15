package com.example.sergey.departmenttest.feature.splash

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.sergey.departmenttest.extansion.addRestartedListener
import com.example.sergey.departmenttest.feature.animation.EmptyAnimatorListener

class PulsarIndicator @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleRes: Int = 0
) : View(context, attrs, defStyleRes) {

    companion object {
        private const val MAX_RADIUS = 150f
        private const val DURATION_ANIMATION = 2000L
    }

    private val layers: List<IndicatorLayer> = createLayers()
    private val animators: MutableList<ValueAnimator> = ArrayList()

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
        createAllAnimators()
        animators.forEach { it.start() }
    }

    private fun stopAnimation() {
        animators.forEach { it.cancel() }
        animators.clear()
    }

    private fun createLayers(): List<IndicatorLayer> {
        return listOf(
                IndicatorLayer(
                        maxRadius = MAX_RADIUS,
                        durationAnimation = DURATION_ANIMATION,
                        paint = Paint().apply { color = Color.WHITE }
                ),
                IndicatorLayer(
                        maxRadius = MAX_RADIUS * 85 / 100,
                        startDelay = 500L,
                        durationAnimation = DURATION_ANIMATION - 500L,
                        paint = Paint().apply { color = Color.WHITE }
                ),
                IndicatorLayer(
                        maxRadius = MAX_RADIUS * 65 / 100,
                        startDelay = 750L,
                        durationAnimation = DURATION_ANIMATION - 750L,
                        paint = Paint().apply { color = Color.WHITE }
                )
        )
    }

    private fun createAllAnimators() {
        layers.forEachIndexed { index, layer ->
            animators.add(createAlphaAnimator(layer))

            val radiusAnimator = createRadiusAnimator(layer).apply {
                if (index == 0) {
                    addUpdateListener { invalidate() }
                    return@apply
                }
                if (index == layers.lastIndex) {
                    addRestartedListener {
                        stopAnimation()
                        startAnimation()
                    }
                }
            }
            animators.add(radiusAnimator)
        }
    }

    private class IndicatorLayer(
            val maxRadius: Float,
            val paint: Paint,
            val startDelay: Long = 0L,
            val durationAnimation: Long = 1000L,
            var currentRadius: Float = 0f,
            val startAlphaDelay: Long = startDelay + 150L,
            val durationAlphaAnimation: Long = durationAnimation - 150L
    )

    private fun createAlphaAnimator(layer: IndicatorLayer) =
            ValueAnimator.ofInt(255, 0).apply {
                startDelay = layer.startAlphaDelay
                duration = layer.durationAlphaAnimation
                addListener(object : EmptyAnimatorListener() {
                    override fun onAnimationEnd(animation: Animator?) {
                        layer.paint.alpha = 255
                    }
                })
                addUpdateListener { layer.paint.alpha = it.animatedValue as Int }
            }

    private fun createRadiusAnimator(layer: IndicatorLayer) =
            ValueAnimator.ofFloat(0f, layer.maxRadius).apply {
                startDelay = layer.startDelay
                duration = layer.durationAnimation
                addListener(object : EmptyAnimatorListener() {
                    override fun onAnimationEnd(animation: Animator?) {
                        layer.currentRadius = 0f
                    }
                })
                addUpdateListener { layer.currentRadius = it.animatedValue as Float }
            }
}