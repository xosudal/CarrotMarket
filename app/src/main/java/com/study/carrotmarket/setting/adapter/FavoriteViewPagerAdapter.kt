package com.study.carrotmarket.setting.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.study.carrotmarket.setting.fragment.FavoriteFragment

class FavoriteViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return FavoriteFragment()
    }
}