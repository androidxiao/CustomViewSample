package com.black.multi.customviewsample.demo03

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * Created by wei.
 * Date: 2020/6/17 14:25
 * Desc:
 */
class VpAdapter(fm:FragmentManager,fragments:ArrayList<Fragment>) : FragmentPagerAdapter(fm){

    private val mFragments = fragments

    override fun getItem(position: Int): Fragment{
        return  mFragments[position]
    }

    override fun getCount(): Int {
        return  mFragments.size
    }

}