package com.black.multi.customviewsample.demo05

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.animation.doOnEnd
import com.black.multi.customviewsample.utils.dp2Px
import com.black.multi.customviewsample.utils.log
import kotlin.math.sqrt

/**
 * Created by wei.
 * Date: 2020/6/27 下午3:25
 * Description:
 */
class View58 @JvmOverloads constructor(context: Context,attributeSet: AttributeSet,defStyle:Int = 0):View(context,attributeSet,defStyle) {

    private var mPaint = Paint()
    private var mZColor = Color.BLUE
    private var mSColor = Color.GRAY
    private var mC = Color.RED
    private var mType = DrawType.y
    private var mRect = Rect()
    private var mPath = Path()
    private var mDuration = 800L
    private var mStokeWidth = dp2Px(2,context.resources).toFloat()


    init {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mStokeWidth
        mPaint.strokeCap = Paint.Cap.BUTT
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
        when(mType){
            DrawType.z ->{
                mRect.left = 0
                mRect.top = 0
                mRect.right = width
                mRect.bottom = height
                mPaint.color = mZColor
                canvas.drawRect(mRect,mPaint)
            }
            DrawType.y ->{
                mPaint.color = mC
                canvas.drawCircle((width/2).toFloat(), (height/2).toFloat(),
                    (width/2).toFloat()-mStokeWidth,mPaint)
            }
            DrawType.s ->{
                canvas.save()
                canvas.translate((width/2).toFloat(), (height/2).toFloat())
                mPath.moveTo(0f,(-width/2).toFloat())
                mPath.lineTo((width)/2f,0f)
                mPath.lineTo((-width)/2f,0f)
                mPath.close()
                mPaint.color = mSColor
                canvas.drawPath(mPath,mPaint)
                canvas.restore()
            }
        }

    }


    fun startAnim(){
        val objAnim = ObjectAnimator.ofFloat(this,"translationY",0f,-height.toFloat())
        objAnim.duration = mDuration
        objAnim.interpolator = AccelerateInterpolator()
        objAnim.addListener(object :Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                down()
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationStart(animation: Animator) {
                rotateX()
            }


        })
        objAnim.start()
    }

     private fun rotateX(){
         val objAnim = ObjectAnimator.ofFloat(this,"rotation",0f,180f)
         objAnim.duration = mDuration
         objAnim.addListener(object :Animator.AnimatorListener{
             override fun onAnimationRepeat(animation: Animator) {
             }

             override fun onAnimationEnd(animation: Animator) {
             }

             override fun onAnimationCancel(animation: Animator) {
             }

             override fun onAnimationStart(animation: Animator) {
             }


         })
         objAnim.start()
     }

    private fun down(){
        val objAnim = ObjectAnimator.ofFloat(this,"translationY",-height.toFloat(),0f)
        objAnim.duration = mDuration
        objAnim.interpolator = AccelerateInterpolator()
        objAnim.addListener(object :Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                changeType()
                startAnim()
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationStart(animation: Animator) {
                rotateX()
            }


        })
        objAnim.start()
    }

    private fun changeType(){
        mType = when (mType) {
            DrawType.y ->{
                DrawType.z
            }
            DrawType.z ->{
                DrawType.s
            }
            DrawType.s ->{
                DrawType.y
            }
        }
        invalidate()
    }

    enum class DrawType{
        //正方形，圆，三角形
        z,y,s
    }
}