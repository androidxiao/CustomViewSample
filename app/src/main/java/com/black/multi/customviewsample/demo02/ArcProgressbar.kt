package com.black.multi.customviewsample.demo02

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.black.multi.customviewsample.R
import com.black.multi.customviewsample.utils.dp2Px
import com.black.multi.customviewsample.utils.log
import com.black.multi.customviewsample.utils.sp2Px

/**
 * Created by wei.
 * Date: 2020/6/16 9:42
 * Desc:
 */
class ArcProgressbar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
    defStyle: Int = 0
) : View(context, attributeSet, defStyle) {

    private var mOuterPaint = Paint()
    private var mInnerPaint = Paint()
    private var mTextPaint = TextPaint()
    private var mOuterColor = Color.BLACK
    private var mInnerColor = Color.BLUE
    private var mProgressWidth = 5
    private var mText = ""
    private var mTextColor = Color.BLACK
    private var mCurProgress = 0
    private var mMaxProgress = 0
    private var mTextSize = 15
    private val rectF = RectF()
    private val rect = Rect()
    private val startAngle = 135f
    private var sweepAngle = 270f
    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.ArcProgressbar)
        mOuterColor = ta.getColor(R.styleable.ArcProgressbar_outerColor, mOuterColor)
        mInnerColor = ta.getColor(R.styleable.ArcProgressbar_innerColor, mInnerColor)
        mProgressWidth =
            ta.getInt(R.styleable.ArcProgressbar_progressWidth, dp2Px(mProgressWidth, resources))
        mText = ta.getString(R.styleable.ArcProgressbar_progressText).toString()
        mTextColor = ta.getColor(R.styleable.ArcProgressbar_progressTextColor, mTextColor)
        mTextSize = ta.getDimensionPixelSize(
            R.styleable.ArcProgressbar_customTextSize,
            sp2Px(mTextSize, resources)
        )
        ta.recycle()

        mOuterPaint.isAntiAlias = true
        mOuterPaint.color = mOuterColor
        mOuterPaint.style = Paint.Style.STROKE
        mOuterPaint.strokeWidth = mProgressWidth.toFloat()


        mInnerPaint.isAntiAlias = true
        mInnerPaint.color = mInnerColor
        mInnerPaint.style = Paint.Style.STROKE
        mInnerPaint.strokeWidth = mProgressWidth.toFloat()

        mTextPaint.isAntiAlias = true
        mTextPaint.color = mTextColor
        mTextPaint.textSize = mTextSize.toFloat()
        mTextPaint.strokeWidth = mProgressWidth.toFloat()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


        //这里需要使用 measureWidth 和 measureHeight , 如果使用 width 和 height 不一定有值
        var widthSize = measuredWidth
        var heightSize = measuredHeight

        if (widthSize > heightSize) widthSize = heightSize

        if (heightSize > widthSize) heightSize = widthSize

        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //画外圆
        val left = mProgressWidth /2
        val top = mProgressWidth /2
        //如果不减去 画笔的宽度，画出来的图形会显示不全
        val right = width - mProgressWidth / 2
        val bottom = height - mProgressWidth / 2
        rectF.left = left.toFloat()
        rectF.top = top.toFloat()
        rectF.right = right.toFloat()
        rectF.bottom = bottom.toFloat()

        canvas.drawArc(rectF, startAngle, sweepAngle, false, mOuterPaint)

        if (mMaxProgress == 0) return

        //画外圆
        val percent = mCurProgress / mMaxProgress.toFloat()
        sweepAngle *= percent
        canvas.drawArc(rectF, startAngle, sweepAngle, false, mInnerPaint)


        //画文字
        mTextPaint.getTextBounds(mText, 0, mText.length, rect)
        val startX = (height / 2 - mProgressWidth) - rect.width() / 2
        val fontMetricsInt = mTextPaint.fontMetricsInt
        val vy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom
        val baseLine = height / 2 + vy
        canvas.drawText(sweepAngle.toInt().toString(), startX.toFloat(), baseLine.toFloat(), mTextPaint)

    }

    fun setCurProgress(progress: Int) {
        mCurProgress = progress
        invalidate()
    }

    fun setMaxProgress(progress: Int) {
        mMaxProgress = progress
    }
}