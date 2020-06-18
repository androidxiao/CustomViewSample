package com.black.multi.customviewsample

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import com.black.multi.customviewsample.demo03.TabLayoutActivity
import com.black.multi.customviewsample.utils.log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        custom_text.setOnClickListener {
//            arcProgress()
//            changeTextColor()
            toTabPage()
        }
    }

    private fun toTabPage(){
        val intent = Intent(this,TabLayoutActivity::class.java)
        startActivity(intent)
    }

    private fun changeTextColor(){
        val valueAnimator = ValueAnimator.ofFloat(0f,1.0f)
        valueAnimator.duration = 3000
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            log("$value")
            changeTextColor.setCurProgress(value)
        }
        valueAnimator.start()
    }

    private fun arcProgress(){
        val maxProgress = 3000
        val valueAnimator = ValueAnimator.ofInt(0,2500)
        arc_progressbar.setMaxProgress(maxProgress)
        valueAnimator.interpolator = AccelerateInterpolator()
        valueAnimator.duration = 2500
        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Int
            arc_progressbar.setCurProgress(value)
        }
        valueAnimator.start()
    }
}