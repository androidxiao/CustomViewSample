package com.black.multi.customviewsample.demo06

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.black.multi.customviewsample.R
import com.black.multi.customviewsample.utils.dp2Px
import com.black.multi.customviewsample.utils.log

/**
 * Created by wei.
 * Date: 2020/6/30 下午2:24
 * Description:
 */
class KRatingbar @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet?,
    defStyle: Int = 0
) : View(context, attributes, defStyle) {

    private var mCount = 5
    private var mNormalBitmap: Bitmap
    private var mSelectBitmap: Bitmap
    private var mStarPadding = dp2Px(5, resources)
    private var mCurSelect = -1

    init {
        val ta = context.obtainStyledAttributes(attributes, R.styleable.KRatingbar)
        mCount = ta.getInt(R.styleable.KRatingbar_starNum, 5)
        mStarPadding = ta.getInt(R.styleable.KRatingbar_starPadding, dp2Px(5, resources))
        val normalId = ta.getResourceId(R.styleable.KRatingbar_starNormal, -1)
        if (normalId == 1.unaryMinus()) throw RuntimeException("starNormal can't be empty")
        mNormalBitmap = BitmapFactory.decodeResource(resources, normalId)
        val selectId = ta.getResourceId(R.styleable.KRatingbar_starSelect, -1)
        if (selectId == 1.unaryMinus()) throw RuntimeException("starSelect can't be empty")
        mSelectBitmap = BitmapFactory.decodeResource(resources, selectId)
        ta.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width =
            mNormalBitmap.width * mCount + mStarPadding * (mCount - 1) + paddingLeft + paddingRight
        val height = mNormalBitmap.height + paddingTop + paddingBottom
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until mCount) {
            var x = i * mNormalBitmap.width
            if (i != 0) {
                canvas.translate(mStarPadding.toFloat(), 0f)
            }
            if (i < mCurSelect) {
                canvas.drawBitmap(mSelectBitmap, x.toFloat(), 0f, null)
            } else {
                canvas.drawBitmap(mNormalBitmap, x.toFloat(), 0f, null)
            }

        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {
                val count = event.x.toInt() / (mNormalBitmap.width+mStarPadding) + 1
                if (mCurSelect == count && count != 1) {
                    return false
                }
                if (count < 0) {
                    mCurSelect = 0
                }
                if (count > mCount) {
                    mCurSelect = mCount
                }
                mCurSelect = count
                invalidate()
            }
        }
        return true
    }

}