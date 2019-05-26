package com.quangtd.hlovepuzzle.ui.component

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

/**
 * Created by QuangTD
 * on 1/15/2018.
 */
class SquareHeightImageView(context: Context?, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(heightMeasureSpec, heightMeasureSpec)
    }
}