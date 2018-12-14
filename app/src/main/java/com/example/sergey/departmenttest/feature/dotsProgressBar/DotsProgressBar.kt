package com.example.sergey.departmenttest.feature.dotsProgressBar

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.ColorRes
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import com.example.sergey.departmenttest.R

class DotsProgressBar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleRes: Int = 0
) : View(context, attrs, defStyleRes) {

    companion object {
        private const val DEFAULT_DOT_RADIUS = 15f
        private const val DEFAULT_ACTIVE_DOT_RADIUS = 20f
        private const val DEFAULT_PADDING_DOTS = 10f
        private const val DEFAULT_DOT_COLOR = android.R.color.black
        private const val DEFAULT_COUNT_DOTS = 4
        private const val DEFAULT_ANIMATION_DURATION = 250
        private val DEFAULT_ANIMATION_MODE = AnimationMode.ONLY_RIGHT
    }

    private enum class AnimationMode(private val id: Int) {
        ONLY_RIGHT(0),
        LEFT_RIGHT(1);

        companion object {
            fun getModById(id: Int) =
                    AnimationMode.values().find { it.id == id } ?: ONLY_RIGHT
        }
    }

    private var dotRadius: Float = DEFAULT_DOT_RADIUS
    private var activeDotRadius: Float = DEFAULT_ACTIVE_DOT_RADIUS
    private var paddingDots: Float = DEFAULT_PADDING_DOTS
    @ColorRes
    private var colorDot: Int = DEFAULT_DOT_COLOR
    private var countDots: Int = DEFAULT_COUNT_DOTS
    private var durationAnimation: Int = DEFAULT_ANIMATION_DURATION
    private var animationMode: AnimationMode = DEFAULT_ANIMATION_MODE

    private val dots: MutableList<Dot> = ArrayList()
    private var dotsAnimator: ValueAnimator? = null

    init {
        obtainStyledAttributes(context, attrs)
        initDots()
    }

    @SuppressLint("ResourceAsColor")
    private val paint = Paint().apply { color = colorDot }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = (countDots - 1) * (2 * dotRadius + paddingDots) + 2 * activeDotRadius
        setMeasuredDimension(width.toInt(), 2 * activeDotRadius.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (visibility == VISIBLE) {
            dots.forEach { canvas?.drawCircle(it.center.x, it.center.y, it.radius, paint) }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (visibility == VISIBLE) {
            startAnimation()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnimation()
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility == VISIBLE) {
            startAnimation()
        } else {
            stopAnimation()
        }
    }

    private fun obtainStyledAttributes(context: Context, attrs: AttributeSet? = null) {
        attrs.takeIf { it != null }?.let {
            context.theme.obtainAttributes(it).run { restoreTypedArray() }
        }
    }

    private fun Resources.Theme.obtainAttributes(attrs: AttributeSet): TypedArray {
        return obtainStyledAttributes(attrs, R.styleable.DotsProgressBar, 0, 0)
    }

    private fun TypedArray.restoreTypedArray() {
        try {
            dotRadius = getDimension(R.styleable.DotsProgressBar_dotRadius, DEFAULT_DOT_RADIUS)
            activeDotRadius = getDimension(R.styleable.DotsProgressBar_activeDotRadius, DEFAULT_ACTIVE_DOT_RADIUS)
            paddingDots = getDimension(R.styleable.DotsProgressBar_paddingDots, DEFAULT_PADDING_DOTS)
            @SuppressLint("ResourceAsColor")
            colorDot = getColor(R.styleable.DotsProgressBar_colorDot, DEFAULT_DOT_COLOR)
            countDots = getInteger(R.styleable.DotsProgressBar_countDots, DEFAULT_COUNT_DOTS)
            durationAnimation = getInteger(R.styleable.DotsProgressBar_durationAnimation, DEFAULT_ANIMATION_DURATION)
            animationMode = AnimationMode.getModById(
                    getInt(R.styleable.DotsProgressBar_animationMode, -1)
            )
        } finally {
            recycle()
        }
    }

    private fun initDots() {
        var marginLeft = 0f
        for (i in 0 until countDots) {
            val center = Point(marginLeft + dotRadius, activeDotRadius)
            dots.add(Dot(center, dotRadius))
            marginLeft += 2 * dotRadius + paddingDots
        }
    }

    private fun startAnimation() {
        dotsAnimator = createDotsAnimator()
        dotsAnimator?.start()
    }

    private fun stopAnimation() {
        dotsAnimator?.cancel()
        dotsAnimator = null
    }

    private data class Point(var x: Float, val y: Float)

    private data class Dot(val center: Point, var radius: Float)

    private fun createDotsAnimator(): ValueAnimator {
        return ValueAnimator.ofInt(0, dots.size).apply {
            interpolator = LinearInterpolator()
            duration = durationAnimation.toLong()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = if (animationMode == AnimationMode.ONLY_RIGHT) {
                ValueAnimator.RESTART
            } else {
                ValueAnimator.REVERSE
            }
            addUpdateListener {
                setActiveDot(animatedValue as Int)
                invalidate()
            }
        }
    }

    private fun setActiveDot(position: Int) {
        var marginLeft = 0f
        dots.forEachIndexed { index, dot ->
            dot.radius = if (index == position) activeDotRadius else dotRadius
            dot.center.x = marginLeft + dot.radius
            marginLeft += 2 * dot.radius + paddingDots
        }
    }
}