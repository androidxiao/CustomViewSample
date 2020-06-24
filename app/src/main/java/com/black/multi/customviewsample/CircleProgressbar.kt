package com.black.multi.customviewsample

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.black.multi.customviewsample.utils.dp2Px
import com.black.multi.customviewsample.utils.sp2Px

/**
 * Created by wei.
 * Date: 2020/6/22 下午11:24
 * Description:
 */
class CircleProgressbar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    defStyle: Int = 0
) : View(context, attributeSet, defStyle) {

    private val mOuterPaint = Paint()
    private val mInnerPaint = Paint()
    private val mTextPaint = TextPaint()
    private var mTextColor = Color.BLACK
    private var mTextSize = 15
    private var mOutColor = Color.BLUE
    private var mInnerColor = Color.RED
    private var mProgressWidth = dp2Px(6, context.resources)
    private var mRadiusWidth = dp2Px(40,context.resources).toFloat()
    private var mSweepRadius = 0
    private lateinit var valueAnim:ValueAnimator
    private var mRectF = Rect()

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.CircleProgressbar)
        mOutColor = ta.getColor(R.styleable.CircleProgressbar_outerColor, mOutColor)
        mInnerColor = ta.getColor(R.styleable.CircleProgressbar_innerColor, mInnerColor)
        mTextColor = ta.getColor(R.styleable.CircleProgressbar_customTextColor, mTextColor)
        mTextSize = ta.getDimensionPixelSize(
            R.styleable.CircleProgressbar_customTextSize,
            sp2Px(mTextSize, context.resources)
        )
        mProgressWidth = ta.getInt(R.styleable.CircleProgressbar_progressWidth, mProgressWidth)
        mRadiusWidth = ta.getDimension(R.styleable.CircleProgressbar_radiusWidth,
            dp2Px(mRadiusWidth.toInt(),context.resources).toFloat()
        )
        ta.recycle()


        getPaintByParams(mOuterPaint, mOutColor)
        getPaintByParams(mInnerPaint, mInnerColor)
        getTextPaintByParams()
    }

    private fun getTextPaintByParams() {
        mTextPaint.color = mTextColor
        mTextPaint.textSize = mTextSize.toFloat()
        mTextPaint.isAntiAlias = true
        mTextPaint.isDither = true
    }

    private fun getPaintByParams(paint: Paint, color: Int) {
        paint.color = color
        paint.isAntiAlias = true
        paint.strokeWidth = mProgressWidth.toFloat()
        paint.style = Paint.Style.STROKE
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

        canvas.drawCircle((width/2).toFloat(), (height/2).toFloat(),
            mRadiusWidth-mProgressWidth/2,mOuterPaint)
        val rectF = RectF()
        rectF.left = (mProgressWidth/2).toFloat()
        rectF.top = (mProgressWidth/2).toFloat()
        rectF.right = width.toFloat()-mProgressWidth/2
        rectF.bottom = height.toFloat() - mProgressWidth/2
        canvas.drawArc(rectF, 0F, mSweepRadius.toFloat(),false,mInnerPaint)
        mTextPaint.getTextBounds(mSweepRadius.toString(),0,mSweepRadius.toString().length,mRectF)
        val fontMetricsInt = mTextPaint.fontMetricsInt
        val dy = (fontMetricsInt.bottom-fontMetricsInt.top)/2-fontMetricsInt.bottom+height/2
        val x = mRadiusWidth-mRectF.width()/2
        canvas.drawText(mSweepRadius.toString(), x, dy.toFloat(),mTextPaint)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startAnim()
    }

    private fun startAnim(){
        valueAnim = ValueAnimator.ofInt(0,360)
        valueAnim.duration = 4000
        valueAnim.addUpdateListener {
            val value = it.animatedValue as Int
            mSweepRadius = value
            invalidate()
        }
        valueAnim.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopAnim()
    }

    private fun stopAnim(){
        valueAnim.cancel()
        valueAnim.removeAllUpdateListeners()
    }
}