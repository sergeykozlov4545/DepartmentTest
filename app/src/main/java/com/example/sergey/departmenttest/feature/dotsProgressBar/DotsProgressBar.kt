package com.example.sergey.departmenttest.feature.dotsProgressBar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.annotation.ColorRes
import android.util.AttributeSet
import android.view.View
import com.example.sergey.departmenttest.R
import com.example.sergey.departmenttest.feature.animation.AnimationCallback
import com.example.sergey.departmenttest.feature.animation.RepeatableAnimation

class DotsProgressBar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleRes: Int = 0
) : View(context, attrs, defStyleRes), AnimationCallback {

    companion object {
        private const val DEFAULT_DOT_RADIUS = 15f
        private const val DEFAULT_ACTIVE_DOT_RADIUS = 20f
        private const val DEFAULT_PADDING_DOTS = 10f
        private const val DEFAULT_DOT_COLOR = android.R.color.black
        private const val DEFAULT_COUNT_DOTS = 4
        private const val DEFAULT_ANIMATION_DURATION = 100
        private const val ONLY_RIGHT_ANIMATION = 0
        private const val LEFT_RIGHT_ANIMATION = 1
    }

    private val dotRadius: Float
    private val activeDotRadius: Float
    private val paddingDots: Float
    @ColorRes
    private val colorDot: Int
    private val countDots: Int
    private var activeDotsPosition = 0

    private var animation: RepeatableAnimation? = null
    private val durationAnimation: Int

    private var positionDelta = 1
    private val animationMode: Int

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.DotsProgressBar, 0, 0)
                .apply {
                    try {
                        dotRadius = getDimension(R.styleable.DotsProgressBar_dotRadius, DEFAULT_DOT_RADIUS)
                        activeDotRadius = getDimension(R.styleable.DotsProgressBar_activeDotRadius, DEFAULT_ACTIVE_DOT_RADIUS)
                        paddingDots = getDimension(R.styleable.DotsProgressBar_paddingDots, DEFAULT_PADDING_DOTS)
                        @SuppressLint("ResourceAsColor")
                        colorDot = getColor(R.styleable.DotsProgressBar_colorDot, DEFAULT_DOT_COLOR)
                        countDots = getInteger(R.styleable.DotsProgressBar_countDots, DEFAULT_COUNT_DOTS)
                        durationAnimation = getInteger(R.styleable.DotsProgressBar_durationAnimation, DEFAULT_ANIMATION_DURATION)
                        animationMode = getInt(R.styleable.DotsProgressBar_animationMode, ONLY_RIGHT_ANIMATION)
                    } finally {
                        recycle()
                    }
                }
    }

    @SuppressLint("ResourceAsColor")
    private val paint = Paint().apply {
        color = colorDot
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = (countDots - 1) * (2 * dotRadius + paddingDots) + 2 * activeDotRadius
        setMeasuredDimension(width.toInt(), 2 * activeDotRadius.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (visibility == VISIBLE) {
            drawCircles(canvas)
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

    override fun onAnimationRepeat() {
        when (animationMode) {
            ONLY_RIGHT_ANIMATION -> {
                activeDotsPosition += positionDelta
                if (activeDotsPosition == countDots) {
                    activeDotsPosition = 0
                }
            }
            LEFT_RIGHT_ANIMATION -> {
                activeDotsPosition += positionDelta
                if (activeDotsPosition !in 1 until (countDots - 1)) {
                    positionDelta *= -1
                }
            }
            else -> {
            }
        }
    }

    override fun applyTransformation() {
        invalidate()
    }

    private fun drawCircles(canvas: Canvas?) {
        var marginLeft = 0f
        for (i in 0 until countDots) {
            val dotRadius = if (i == activeDotsPosition) activeDotRadius else dotRadius
            canvas?.drawCircle(marginLeft + dotRadius, height.toFloat() / 2, dotRadius, paint)
            marginLeft += 2 * dotRadius + paddingDots
        }
    }

    private fun startAnimation() {
        if (animation != null) return
        animation = RepeatableAnimation(durationAnimation, this)
        startAnimation(animation)
    }

    private fun stopAnimation() {
        animation.takeIf { it != null }?.run {
            cancel()
            detach()
            animation = null
        }
    }
}