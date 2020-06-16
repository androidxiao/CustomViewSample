package com.black.multi.customviewsample.utils

import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import com.black.multi.customviewsample.BuildConfig

/**
 * Created by wei.
 * Date: 2020/6/16 9:45
 * Desc:
 */
const val TAG = "ez"

fun sp2Px(data: Int,resources:Resources): Int {
    //比如：我想要一个 20(data)(第二个参数)SP(第一个参数)在当前设备上对应的 px 值。
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, data.toFloat(), resources.displayMetrics).toInt()
}

fun dp2Px(data:Int,resources: Resources):Int{
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, data.toFloat(),resources.displayMetrics).toInt()
}

fun log(msg:String){
    if(BuildConfig.DEBUG) {
        Log.d(TAG, "log: $msg")
    }
}