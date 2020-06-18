package com.black.multi.customviewsample.demo03

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.black.multi.customviewsample.R
import com.black.multi.customviewsample.databinding.ActivityTablayoutBinding
import com.google.android.material.tabs.TabLayout
import java.lang.Exception

/**
 * Created by wei.
 * Date: 2020/6/17 14:02
 * Desc:
 */
class TabLayoutActivity : AppCompatActivity(){

    private val mTitles =
        arrayOf("关注", "推荐", "合肥", "热点","幸福里","电影","生活","影视","足球","篮球","乒乓球","排球","羽毛球")
    private val mFragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityTablayoutBinding>(this, R.layout.activity_tablayout)

        mFragments.add(TabFragment.getInstance(mTitles[0]))
        mFragments.add(TabFragment.getInstance(mTitles[1]))
        mFragments.add(TabFragment.getInstance(mTitles[2]))
        mFragments.add(TabFragment.getInstance(mTitles[3]))
        mFragments.add(TabFragment.getInstance(mTitles[4]))
        mFragments.add(TabFragment.getInstance(mTitles[5]))
        mFragments.add(TabFragment.getInstance(mTitles[6]))
        mFragments.add(TabFragment.getInstance(mTitles[7]))
        mFragments.add(TabFragment.getInstance(mTitles[8]))
        mFragments.add(TabFragment.getInstance(mTitles[9]))
        mFragments.add(TabFragment.getInstance(mTitles[10]))
        mFragments.add(TabFragment.getInstance(mTitles[11]))
        mFragments.add(TabFragment.getInstance(mTitles[12]))

        binding.tabLayout.setupWithViewPager(binding.vp)
        binding.vp.adapter = VpAdapter(supportFragmentManager,mFragments)
        for (i  in 0 until((binding.vp.adapter as VpAdapter).count)) {
            val tab = binding.tabLayout.getTabAt(i)
            val view = LayoutInflater.from(applicationContext).inflate(R.layout.item_tab, null)
            val textView =  view.findViewById<ChangeColorTextView>(R.id.changeTextColor)
            textView?.text = mTitles[i]
            if(i == 0){
                textView.setChangeColor(resources.getColor(R.color.red))
            }else{
                textView.setNormalColor(resources.getColor(R.color.black))
            }
            tab?.customView = textView
        }

        binding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
//                tab.customView!!.findViewById<ChangeColorTextView>(R.id.changeTextColor).isSelected = false
//                tab.customView!!.findViewById<ChangeColorTextView>(R.id.changeTextColor).setChangeColor(ContextCompat.getColor(applicationContext,R.color.black))
//                tab.customView!!.findViewById<ChangeColorTextView>(R.id.changeTextColor).setTextColor(ContextCompat.getColor(applicationContext,R.color.black))
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
//                tab.customView!!.findViewById<ChangeColorTextView>(R.id.changeTextColor).isSelected = true
//                binding.vp.currentItem = tab.position;
            }
        })

        binding.vp.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                val curTab = binding.tabLayout.getTabAt(position)?.customView as ChangeColorTextView
                curTab?.setDirection(ChangeColorTextView.Direction.Right_To_Left)
                curTab?.setCurProgress(1-positionOffset)

                try {
                    val nextTab =
                        binding.tabLayout.getTabAt(position + 1)?.customView as ChangeColorTextView
                    nextTab?.setDirection(ChangeColorTextView.Direction.Left_To_Right)
                    nextTab?.setCurProgress(positionOffset)
                } catch (e: Exception) {

                }
            }

            override fun onPageSelected(position: Int) {
            }

        })
    }
}