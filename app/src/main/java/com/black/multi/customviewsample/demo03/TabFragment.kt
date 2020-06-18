package com.black.multi.customviewsample.demo03

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.black.multi.customviewsample.R

/**
 * Created by wei.
 * Date: 2020/6/17 14:18
 * Desc:
 */
const val TAG = "ez"
class TabFragment : Fragment(){

    companion object{

        fun getInstance(text:String):TabFragment {
            val tabFragment = TabFragment()
            val bundle = Bundle()
            bundle.putString("text",text)
            tabFragment.arguments = bundle
            return tabFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_tab,container,false)

        val text = arguments?.getString("text")
        view.findViewById<TextView>(R.id.tv).text = text
        Log.d(TAG, "onCreateView: $text")
        return view
    }
}