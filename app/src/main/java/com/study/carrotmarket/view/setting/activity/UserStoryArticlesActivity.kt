package com.study.carrotmarket.view.setting.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.study.carrotmarket.R
import com.study.carrotmarket.view.setting.adapter.UserStoryViewPagerAdapter
import kotlinx.android.synthetic.main.activity_user_story_articles.*
import kotlinx.android.synthetic.main.toolbar.*

class UserStoryArticlesActivity : AppCompatActivity() {
    private val tabTitle : Array<String> = arrayOf("내 게시글", "내 댓글")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_story_articles)
        settingToolbar()

        userstory_viewpager2.adapter = UserStoryViewPagerAdapter(this)
        TabLayoutMediator(userstory_tab_layout, userstory_viewpager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
        val tabMode = intent.getIntExtra("tab",0)
        userstory_tab_layout.getTabAt(tabMode)?.select()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun settingToolbar() {
        setSupportActionBar(toolbar).apply {
            title = null
        }
        toolbar_title.text = "동네생활 글"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}