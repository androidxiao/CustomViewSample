package com.black.multi.customviewsample.demo03

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.*
import com.black.multi.customviewsample.R
import com.black.multi.customviewsample.databinding.ActivityTablayoutBinding
import com.google.android.material.tabs.TabLayout

/**
 * Created by wei.
 * Date: 2020/6/17 14:02
 * Desc:
 */
class TabLayoutActivity : AppCompatActivity() {

    private val mTitles =
        arrayOf("关注", "推荐", "合肥", "热点", "幸福里", "电影", "生活", "影视", "足球", "篮球", "乒乓球", "排球", "羽毛球")
    private val mFragments = ArrayList<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityTablayoutBinding>(
            this,
            R.layout.activity_tablayout
        )

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
        binding.vp.adapter = VpAdapter(supportFragmentManager, mFragments)
        val count = (binding.vp.adapter as VpAdapter).count
        //动态修改 tab 为我们自定的 textview
        for (i in 0 until count) {
            val tab = binding.tabLayout.getTabAt(i)
            val view = LayoutInflater.from(applicationContext).inflate(R.layout.item_tab, null)
            val textView = view.findViewById<ChangeColorTextView>(R.id.changeTextColor)
            textView?.text = mTitles[i]
            tab?.customView = textView
        }
        //是不是点击，如果是，直接 setCurrentItem = curPosition
        var isClickTab = false
        //点击后，恢复上一个 position 文字为正常颜色
        var prePosition = 0

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                prePosition = tab.position
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                isClickTab = true
                (tab.customView as ChangeColorTextView).setCurProgress(1f)
                binding.vp.currentItem = tab.position
            }
        })



        binding.vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    SCROLL_STATE_IDLE -> {
                    }
                    SCROLL_STATE_DRAGGING -> {
                        //当手动触发滑动时，需要重置
                        isClickTab = false
                    }
                    SCROLL_STATE_SETTLING -> {
                    }
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                if (!isClickTab) {
                    isClickTab = false
                    val curTab =
                        binding.tabLayout.getTabAt(position)?.customView as ChangeColorTextView
                    curTab?.setDirection(ChangeColorTextView.Direction.Right_To_Left)
                    curTab?.setCurProgress(1 - positionOffset)

                    try {
                        val nextTab =
                            binding.tabLayout.getTabAt(position + 1)?.customView as ChangeColorTextView
                        nextTab?.setDirection(ChangeColorTextView.Direction.Left_To_Right)
                        nextTab?.setCurProgress(positionOffset)
                    } catch (e: Exception) {

                    }
                }

                if (isClickTab) {
                    val tab =
                        binding.tabLayout.getTabAt(prePosition)?.customView as ChangeColorTextView
                    tab.setCurProgress(0f)
                }
            }

            override fun onPageSelected(position: Int) {
            }

        })
    }
}