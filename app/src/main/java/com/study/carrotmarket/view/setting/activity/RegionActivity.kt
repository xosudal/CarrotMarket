package com.study.carrotmarket.view.setting.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.LocationInfo
import com.study.carrotmarket.presenter.setting.RegionPresenter
import com.study.carrotmarket.view.setting.adapter.Region
import com.study.carrotmarket.view.setting.adapter.RegionRecyclerView
import kotlinx.android.synthetic.main.activity_region.*
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
            setRegionList(presenter.getRegionList())
        }

        region_recyclerview.apply {
            adapter = regionRecyclerView
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

        region_layout_current_location_find.setOnClickListener {
                presenter.calRegionListByDistance()
                regionRecyclerView.setRegionList(presenter.getRegionList())
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