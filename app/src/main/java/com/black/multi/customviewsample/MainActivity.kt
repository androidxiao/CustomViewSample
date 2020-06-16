package com.black.multi.customviewsample

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        custom_text.setOnClickListener {
            arcProgress()
        }
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