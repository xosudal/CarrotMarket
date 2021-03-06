package com.study.carrotmarket.view.setting.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.study.carrotmarket.view.setting.fragment.HiddenSalesFragment
import com.study.carrotmarket.view.setting.fragment.SalesCompleteFragment
import com.study.carrotmarket.view.setting.fragment.SalesFragment

class SalesViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SalesFragment()
            1 -> SalesCompleteFragment()
            else -> HiddenSalesFragment()
        }
    }

}