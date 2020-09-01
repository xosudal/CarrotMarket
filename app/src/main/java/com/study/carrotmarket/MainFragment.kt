package com.study.carrotmarket

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.study.carrotmarket.adapter.MainViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_main.*
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.jaredrummler.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment(){

    private val TAG = this.javaClass.simpleName

    private lateinit var rView: View
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView")
        super.onCreateView(inflater, container, savedInstanceState)


        rView = inflater.inflate(R.layout.fragment_main, container, false)

//        val spinner = rView.findViewById(R.id.main_fragment_spinner) as MaterialSpinner
        val spinner = rView.main_fragment_spinner
        spinner.setItems("Hello", "My Name is", "Taeyang")
        spinner.popupWindow.width = 200
        spinner.setOnItemSelectedListener(
            MaterialSpinner.OnItemSelectedListener<String>() { materialSpinner: MaterialSpinner, i: Int, l: Long, s: String ->
                Log.d(TAG, s)
            }
        )
        val vp = rView.findViewById(R.id.viewpager_main_fragment) as ViewPager
        vp.let {
            it.adapter = MainViewPagerAdapter(childFragmentManager)
            it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(rView.main_tablayout))
        }
        val tabLayout = rView.main_tablayout
        Log.d(TAG, "tablayout null ? ${tabLayout == null}")
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                vp.currentItem = tab?.position!!
            }

        })
            return rView

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")

    }

}