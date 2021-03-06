package com.black.multi.customviewsample.demo01

import android.content.Context
import android.graphics.*
import android.nfc.Tag
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import com.black.multi.customviewsample.R
import com.black.multi.customviewsample.utils.sp2Px

/**
 * Created by wei.
 * Date: 2020/6/15 上午12:34
 * Description:
 */

const val DEFAULT_TEXT_SIZE = 15

class CustomTextView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet?,
    defStyle: Int = 0
) : View(context, attr, defStyle) {

    private var mPaint: Paint = Paint()
    private var mText: String?
    private var mTextSize = 0.0f
    private var mTextColor = Color.BLACK
    private val rect = Rect()

    init {
        val ta = context.obtainStyledAttributes(attr, R.styleable.CustomTextView)
        mText = ta.getString(R.styleable.CustomTextView_customText)
        mTextSize = ta.getDimensionPixelSize(
            R.styleable.CustomTextView_customTextSize,
            sp2Px(DEFAULT_TEXT_SIZE,resources)
        ).toFloat()
        mTextColor = ta.getColor(R.styleable.CustomTextView_customTextColor, mTextColor)
        ta.recycle()

        mPaint.isAntiAlias = true
        mPaint.color = mTextColor
        mPaint.textSize = mTextSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED) {//UNSPECIFIED：是为了兼容最外层是 ScrollView
            mPaint.getTextBounds(mText, 0, mText!!.length, rect)
            widthSize = rect.width() + paddingLeft + paddingRight
        }

        if (heightMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.UNSPECIFIED) {//UNSPECIFIED：是为了兼容最外层是 ScrollView
            val fontMetricsInt = mPaint.fontMetricsInt
            heightSize  = fontMetricsInt.bottom-fontMetricsInt.top + paddingLeft + paddingRight
        }

        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val fontMetricsInt = mPaint.fontMetricsInt
        val dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom
        val vY = height / 2 + dy
        canvas?.drawText(mText!!, paddingLeft.toFloat(), vY.toFloat(), mPaint)
    }

}