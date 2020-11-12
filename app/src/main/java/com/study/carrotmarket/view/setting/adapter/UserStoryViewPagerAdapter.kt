package com.study.carrotmarket.view.setting.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.study.carrotmarket.view.setting.fragment.UserArticleFragment
import com.study.carrotmarket.view.setting.fragment.UserCommentFragment

class UserStoryViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> UserArticleFragment()
            1 -> UserCommentFragment()
            else -> UserArticleFragment()
        }
    }

}