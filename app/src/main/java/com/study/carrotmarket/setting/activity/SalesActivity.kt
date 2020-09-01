package com.study.carrotmarket.setting.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.study.carrotmarket.R
import com.study.carrotmarket.setting.adapter.SalesViewPagerAdapter
import kotlinx.android.synthetic.main.activity_sales.*

class SalesActivity : AppCompatActivity() {
    private val tabTitle : Array<String> = arrayOf("판매중", "거래완료", "숨김")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)

        sales_viewpager2.adapter = SalesViewPagerAdapter(this)
        TabLayoutMediator(sales_tab_layout, sales_viewpager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        sales_btn_back.setOnClickListener {
            finish()
        }
    }
}