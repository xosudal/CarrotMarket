package com.study.carrotmarket.setting.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.study.carrotmarket.R
import com.study.carrotmarket.setting.adapter.FavoriteViewPagerAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {
    private val tabTitle : Array<String> = arrayOf("중고거래", "동네홍보", "동네업체", "동네생활")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        favorite_btn_back.setOnClickListener {
            finish()
        }

        favorite_viewpager2.adapter = FavoriteViewPagerAdapter(this)
        favorite_viewpager2.offscreenPageLimit = 4
        TabLayoutMediator(favorite_tab_layout, favorite_viewpager2) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()

        favorite_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("heo","${tab?.position}")
/*                when(tab?.position) {

                    supportFragmentManager.findFragmentById(R.id.)
                }*/
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

    }
}