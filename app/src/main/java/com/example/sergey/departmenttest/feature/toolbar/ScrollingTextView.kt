package com.example.sergey.departmenttest.feature.toolbar

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView

class ScrollingTextView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleRes: Int = 0
) : TextView(context, attrs, defStyleRes) {

    init {
        ellipsize = TextUtils.TruncateAt.MARQUEE
        setSingleLine(true)
        marqueeRepeatLimit = -1
        isSelected = true
    }
}