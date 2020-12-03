package com.study.carrotmarket.view.setting.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.LocationInfo
import com.study.carrotmarket.view.setting.adapter.RegionShowRecyclerView
import kotlinx.android.synthetic.main.activity_region_show.*
import kotlinx.android.synthetic.main.layout_region_show.view.*
import kotlinx.android.synthetic.main.toolbar.*

class RegionShowActivity : AppCompatActivity() {
    private lateinit var regionShowRecyclerView:RegionShowRecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region_show)

        regionShowRecyclerView = RegionShowRecyclerView()
        regionShowRecyclerView.setRegionList(intent.getParcelableArrayListExtra("LIST")?: arrayListOf())
        region_show_recyclerview.apply {
            adapter = regionShowRecyclerView
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
        settingToolbar()
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
        toolbar_title.text = getString(R.string.region_count_text, regionShowRecyclerView.itemCount)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}