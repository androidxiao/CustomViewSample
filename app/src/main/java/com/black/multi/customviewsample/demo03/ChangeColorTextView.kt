package com.black.multi.customviewsample.demo03

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.black.multi.customviewsample.R

/**
 * Created by wei.
 * Date: 2020/6/17 8:51
 * Desc:
 */
class ChangeColorTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    defStyle: Int = 0
) : AppCompatTextView(context, attributeSet, defStyle) {

    private var mNormalPaint = TextPaint()
    private var mSelectedPaint = TextPaint()
    private var mNormalColor = Color.BLACK
    private var mSelectedColor = Color.RED
    private val rectF = Rect()
    private val rectText = Rect()
    private var mCurProgress = 0f
    private var mDirection:Direction = Direction.Right_To_Left

    enum class Direction {
        Left_To_Right,
        Right_To_Left
    }

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.ChangeColorTextView)
        mNormalColor = ta.getColor(R.styleable.ChangeColorTextView_normalColor, mNormalColor)
        mSelectedColor = ta.getColor(R.styleable.ChangeColorTextView_selectedColor, mSelectedColor)
        ta.recycle()
        getPaintByParams(mNormalPaint,mNormalColor)
        getPaintByParams(mSelectedPaint,mSelectedColor)
    }

    private fun getPaintByParams(paint: Paint,color:Int) {
        paint.isAntiAlias = true
        paint.isDither = true
        paint.textSize = textSize
        paint.color = color
    }

    override fun onDraw(canvas: Canvas) {
        //这里需要我们自己 canvas
//        super.onDraw(canvas)

        //从左到右
//        canvas.save()
//        rectF.left = 0f
//        rectF.top = 0f
//        rectF.right = mCurProgress * width
//        rectF.bottom = height.toFloat()
//        canvas.clipRect(rectF)
//        var fontMetricsInt = mNormalPaint.fontMetricsInt
//        var vy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom
//        var baseLine = vy + height / 2
//        mNormalPaint.getTextBounds(text.toString(), 0, text.length, rectText)
        //文字起始位置
//        var startX = (width - rectText.width()) / 2
//        canvas.drawText(text.toString(), startX.toFloat(), baseLine.toFloat(), mNormalPaint)
//        canvas.restore()

        if (mDirection == Direction.Left_To_Right) {
            drawContent(canvas,mNormalPaint, (mCurProgress*width).toInt(),width)
            drawContent(canvas,mSelectedPaint,0, (mCurProgress*width).toInt())
        }else{
            drawContent(canvas,mSelectedPaint, (width-mCurProgress*width).toInt(),width)
            drawContent(canvas,mNormalPaint,0, (width-mCurProgress*width).toInt())
        }

        //从右到左
//        canvas.save()
//        rectF.left = (1-mCurProgress) * width
//        rectF.top = 0f
//        rectF.right = width.toFloat()
//        rectF.bottom = height.toFloat()
//        canvas.clipRect(rectF)
//        fontMetricsInt = mNormalPaint.fontMetricsInt
//        vy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom
//        baseLine = vy + height / 2
//        mSelectedPaint.getTextBounds(text.toString(), 0, text.length,rectText)
//        //文字起始位置
//        startX = (width - rectText.width()) / 2
//        canvas.drawText(text.toString(), startX.toFloat(), baseLine.toFloat(), mSelectedPaint)
//        canvas.restore()
    }

    private fun drawContent(canvas: Canvas, paint: TextPaint, left:Int, right: Int) {
        //从左到右
        canvas.save()
        rectF.left = left
        rectF.top = 0
        rectF.right = right
        rectF.bottom = height
        canvas.clipRect(rectF)
        var fontMetricsInt = paint.fontMetricsInt
        //获取文字基线
        var vy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom
        var baseLine = vy + height / 2
        paint.getTextBounds(text.toString(), 0, text.length, rectText)
        //文字起始位置
        var startX = (width - rectText.width()) / 2
        canvas.drawText(text.toString(), startX.toFloat(), baseLine.toFloat(), paint)
        canvas.restore()
    }

    fun setCurProgress(progress:Float){
        mCurProgress = progress
        invalidate()
    }

    fun setDirection(direction: Direction) {
        mDirection = direction
    }

    fun setChangeColor(color:Int){
        mSelectedPaint.color = color
    }

    fun setNormalColor(color: Int) {
        mNormalPaint.color = color
    }
}