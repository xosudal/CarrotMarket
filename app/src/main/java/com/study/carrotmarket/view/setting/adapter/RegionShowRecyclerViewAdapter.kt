package com.study.carrotmarket.view.setting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.LocationInfo
import kotlinx.android.synthetic.main.layout_region_show.view.*

class RegionShowRecyclerView : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var regionList:ArrayList<LocationInfo>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_region_show, parent,false)
        return RegionShowViewHolder(view)
    }

    inner class RegionShowViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = (holder as RegionShowViewHolder).itemView
        if (regionList[position].neighborhood != "") viewHolder.tv_region_show.text = regionList[position].neighborhood
    }

    override fun getItemCount(): Int {
        return regionList.size
    }

    fun setRegionList(list:ArrayList<LocationInfo>) {
        regionList = list
    }
}