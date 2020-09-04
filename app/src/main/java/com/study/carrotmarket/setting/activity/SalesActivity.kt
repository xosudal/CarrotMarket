package com.study.carrotmarket.setting.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.study.carrotmarket.R
import com.study.carrotmarket.setting.adapter.SalesViewPagerAdapter
import kotlinx.android.synthetic.main.activity_sales.*
import kotlinx.android.synthetic.main.toolbar.*

class SalesActivity : AppCompatActivity() {
    private val tabTitle : Array<String> = arrayOf("판매중", "거래완료", "숨김")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)
        settingToolbar()

        sales_viewpager2.adapter = SalesViewPagerAdapter(this)
        TabLayoutMediator(sales_tab_layout, sales_viewpager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
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
        toolbar_title.text = "판매내역"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}