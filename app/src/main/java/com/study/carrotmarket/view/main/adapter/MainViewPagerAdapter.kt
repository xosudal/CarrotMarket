package com.study.carrotmarket.view.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.study.carrotmarket.view.main.MainLivingFragment
import com.study.carrotmarket.view.main.MainMarketFragment

class MainViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val items = listOf<Fragment>(MainMarketFragment(), MainLivingFragment())
    override fun getItem(position: Int): Fragment {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }
}