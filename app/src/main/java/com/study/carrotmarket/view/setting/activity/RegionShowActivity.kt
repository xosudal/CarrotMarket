package com.study.carrotmarket.view.setting.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_region_show.*
import kotlinx.android.synthetic.main.layout_region_show.view.*
import kotlinx.android.synthetic.main.toolbar.*

class RegionShowActivity : AppCompatActivity() {
    private lateinit var regionShowRecyclerView:RegionShowRecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region_show)
        settingToolbar()

        regionShowRecyclerView = RegionShowRecyclerView()
        region_show_recyclerview.apply {
            adapter = regionShowRecyclerView
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
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
        toolbar_title.text = getString(R.string.region_count_text,RegionSettingActivity.regionNearByList.size)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    inner class RegionShowRecyclerView : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = layoutInflater.inflate(R.layout.layout_region_show, parent,false)
            return RegionShowViewHolder(view)
        }

        inner class RegionShowViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = (holder as RegionShowViewHolder).itemView
            if (RegionSettingActivity.regionNearByList[position].neighborhood != "") viewHolder.tv_region_show.text = RegionSettingActivity.regionNearByList[position].neighborhood
        }

        override fun getItemCount(): Int {
            return RegionSettingActivity.regionNearByList.size
        }
    }
}