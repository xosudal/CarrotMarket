package com.study.carrotmarket.setting.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_region.*
import kotlinx.android.synthetic.main.layout_region.view.*
import kotlinx.android.synthetic.main.toolbar_region.*

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

        toolbar_iv_cancel.run {
            setOnClickListener {
                toolbar_edit_tv.text = null
            }
            visibility = View.GONE
        }

        region_back_btn.setOnClickListener {
            finish()
        }

        toolbar_edit_tv.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                toolbar_iv_cancel.visibility = if (count > 0) View.VISIBLE else View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
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

            viewHolder.layout_region.setOnClickListener {
                val intent = Intent(baseContext, RegionSettingActivity::class.java).apply {
                    putExtra("selectList",regionList[position])
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                setResult(RESULT_OK,intent)
                finish()
            }
        }

        override fun getItemCount(): Int {
            return regionList.size
        }
    }

    private fun search(word:String) {
        TODO()
    }

    override fun onResume() {
        super.onResume()
        Log.d("heo","onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.d("heo","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("heo","onDestroy")
    }
}