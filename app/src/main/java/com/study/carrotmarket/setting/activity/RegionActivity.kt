package com.study.carrotmarket.setting.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_region.*
import kotlinx.android.synthetic.main.layout_region.view.*
import kotlinx.android.synthetic.main.toolbar.*

class RegionActivity : AppCompatActivity() {
    private lateinit var regionRecyclerView:RegionRecyclerView
    private lateinit var regionList:List<RegionSettingActivity.StaticList.LocationInfo>
    val currentPosition = RegionSettingActivity.StaticList.LocationInfo(
        "",
        "서울특별시",
        "강서구",
        "가양동",
        37.5648322,
        126.8342406
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region)
        settingToolbar()

        regionRecyclerView = RegionRecyclerView()
        region_recyclerview.apply {
            adapter = regionRecyclerView
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

        regionList = RegionSettingActivity.regionTotalList.sortedBy {
            it.distance
        }

        region_layout_current_location_find.setOnClickListener {
            Toast.makeText(this,"현재 위치를 찾고있어요",Toast.LENGTH_SHORT).show()
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

    inner class RegionRecyclerView : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = layoutInflater.inflate(R.layout.layout_region, parent,false)
            return RegionViewHolder(view)
        }

        inner class RegionViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = (holder as RegionViewHolder).itemView
            viewHolder.tv_region.text = StringBuilder().apply {
                append(regionList[position].province)
                if (regionList[position].province != "") append(" ")
                append(regionList[position].city)
                append(" ")
                append(regionList[position].district)
                if (regionList[position].district != "") append(" ")
                append(regionList[position].neighborhood)
            }.toString()
        }

        override fun getItemCount(): Int {
            return regionList.size
        }
    }
}