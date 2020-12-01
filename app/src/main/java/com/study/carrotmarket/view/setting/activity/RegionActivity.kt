package com.study.carrotmarket.view.setting.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.LocationInfo
import com.study.carrotmarket.model.setting.RegionListModel
import com.study.carrotmarket.presenter.setting.RegionPresenter
import com.study.carrotmarket.view.setting.adapter.Region
import com.study.carrotmarket.view.setting.adapter.RegionRecyclerView
import kotlinx.android.synthetic.main.activity_region.*
import kotlinx.android.synthetic.main.layout_region.view.*
import kotlinx.android.synthetic.main.toolbar_region.*

@RequiresApi(Build.VERSION_CODES.N)
class RegionActivity : AppCompatActivity(), Region {
    private lateinit var regionRecyclerView:RegionRecyclerView

    private lateinit var presenter:RegionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region)

        presenter = RegionPresenter(this)
        presenter.initLocation()

        presenter.createRegionList()

        regionRecyclerView = RegionRecyclerView(this).apply {
            setRegionList(RegionListModel.regionList)
        }

        region_recyclerview.apply {
            adapter = regionRecyclerView
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }



        region_layout_current_location_find.setOnClickListener {
                presenter.calRegionListByDistance()
                regionRecyclerView.setRegionList(RegionListModel.regionList)
                regionRecyclerView.notifyDataSetChanged()
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
                regionRecyclerView.setRegionList(presenter.search(s.toString()))
                regionRecyclerView.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    override fun onResume() {
        super.onResume()
        presenter.registerLocationListener()
    }

    override fun onStop() {
        super.onStop()
        presenter.unRegisterLocationListener()
    }

    override fun sendResultIntent(list:LocationInfo) {
        val intent = Intent(baseContext, RegionSettingActivity::class.java).apply {
            putExtra("selectList",list)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        setResult(RESULT_OK,intent)
        finish()
    }
}