package com.study.carrotmarket.setting.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.study.carrotmarket.R
import com.study.carrotmarket.setting.adapter.UserStoryViewPagerAdapter
import kotlinx.android.synthetic.main.activity_user_story_articles.*

class UserStoryArticlesActivity : AppCompatActivity() {
    private val tabTitle : Array<String> = arrayOf("내 게시글", "내 댓글")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_story_articles)

        userstory_viewpager2.adapter = UserStoryViewPagerAdapter(this)
        TabLayoutMediator(userstory_tab_layout, userstory_viewpager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
        val tabMode = intent.getIntExtra("tab",0)
        userstory_tab_layout.getTabAt(tabMode)?.select()
        userstory_btn_back.setOnClickListener {
            finish()
        }
    }
}