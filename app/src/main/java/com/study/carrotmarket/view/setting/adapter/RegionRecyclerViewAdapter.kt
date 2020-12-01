package com.study.carrotmarket.view.setting.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.LocationInfo
import com.study.carrotmarket.view.setting.activity.RegionSettingActivity
import kotlinx.android.synthetic.main.layout_region.view.*

class RegionRecyclerView(private var region:Region) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var regionList:List<LocationInfo>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_region, parent,false)
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

        viewHolder.layout_region.setOnClickListener {
            region.sendResultIntent(regionList[position])
        }
    }

    override fun getItemCount(): Int {
        return regionList.size
    }

    fun setRegionList(list:List<LocationInfo>) {
        regionList = list
    }
}

interface Region {
    fun sendResultIntent(list:LocationInfo)
}