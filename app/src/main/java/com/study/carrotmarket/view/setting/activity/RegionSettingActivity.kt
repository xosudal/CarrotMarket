package com.study.carrotmarket.view.setting.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.LocationInfo
import com.study.carrotmarket.constant.RegionSettingContract
import com.study.carrotmarket.presenter.setting.RegionSettingPresenter
import kotlinx.android.synthetic.main.activity_region_setting.*
import kotlinx.android.synthetic.main.toolbar.*

const val SELECT_FIRST = 1
const val SELECT_SECOND = 2

@RequiresApi(Build.VERSION_CODES.N)
class RegionSettingActivity : AppCompatActivity(), RegionSettingContract.View {
    private lateinit var presenter:RegionSettingPresenter
    private lateinit var currentPosition: LocationInfo
    private lateinit var selectedList:Pair<LocationInfo?, LocationInfo?>
    private var progressCount:Int = 0
    private var selectedNumber:Int = 1

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region_setting)
        settingToolbar()
        presenter = RegionSettingPresenter().apply {
            view = this@RegionSettingActivity
            setContext(this@RegionSettingActivity)
        }

        region_seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                progressCount = p1
                presenter.setProgressCount(progressCount)
                setImageAlpha(p1)
                presenter.calNearByRegion(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        presenter.loadRegionList()

        region_tv_neighborhood_count.setOnClickListener {
            val intent = Intent(this, RegionShowActivity::class.java).apply {
                putExtra("LIST",presenter.getNearByRegionList())
            }
            startActivity(intent)
        }

        first_frame_layout.setOnClickListener {
            setViewSelectedNeighborhoodLayout(SELECT_FIRST)
        }

        second_frame_layout.setOnClickListener{
            setViewSelectedNeighborhoodLayout(SELECT_SECOND)
        }

        region_iv_first.setOnClickListener {
            startActivityForResult(Intent(this, RegionActivity::class.java), SELECT_FIRST)
        }

        region_iv_second.setOnClickListener {
            startActivityForResult(Intent(this, RegionActivity::class.java), SELECT_SECOND)
        }

        region_close_first.setOnClickListener {

        }

        region_close_second.setOnClickListener {
            closeViewNeighborhood()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SELECT_FIRST -> {
                if (resultCode == RESULT_OK) {
                    selectedList = selectedList.copy(data?.getParcelableExtra("selectList"))
                    selectedList.first.let {
                        if (it != null) {
                            setSelectedNeighborhood(it)
                        }
                    }
                }
            }

            SELECT_SECOND -> {
                if (resultCode == RESULT_OK) {
                    selectedList = selectedList.copy(selectedList.first, data?.getParcelableExtra("selectList"))
                    selectedList.second.let {
                        if (it != null) {
                            setSelectedNeighborhood(it)
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResume() {
        super.onResume()
        progressCount = presenter.getProgressCount()
        selectedNumber = presenter.getSelectedNumber()
        selectedList = presenter.loadSelectedLocationList()
        setViewSelectedNeighborhoodLayout(selectedNumber)
        region_seek_bar.progress = progressCount
        setImageAlpha(progressCount)
        presenter.calNearByRegion(progressCount)
        loadSelectedNeighborhood()
        presenter.sortRegionList(currentPosition)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
        toolbar_title.text = "내 동네 설정"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setImageAlpha(position: Int) {
        when (position) {
            0 -> {
                iv_region_2.alpha = 0.3F
                iv_region_3.alpha = 0.3F
                iv_region_4.alpha = 0.3F
            }
            1 -> {
                iv_region_2.alpha = 0.8F
                iv_region_3.alpha = 0.3F
                iv_region_4.alpha = 0.3F
            }
            2 -> {
                iv_region_2.alpha = 0.8F
                iv_region_3.alpha = 0.6F
                iv_region_4.alpha = 0.3F
            }
            3 -> {
                iv_region_2.alpha = 0.8F
                iv_region_3.alpha = 0.6F
                iv_region_4.alpha = 0.45F
            }
        }
    }

    private fun setSelectedNeighborhood(list: LocationInfo) {
        if (selectedList.first == null) {
            selectedList = selectedList.copy(list, selectedList.second)
        } else {
            selectedList = selectedList.copy(selectedList.first, list)
            setViewNeighborhood(region_tv_second, region_iv_second, selectedList.second?.neighborhood)
        }
        setViewNeighborhood(region_tv_first, region_iv_first, selectedList.first?.neighborhood)
        presenter.saveSelectedLocationList(selectedList.first, selectedList.second)
    }


    private fun loadSelectedNeighborhood() {
        if (selectedList.first == null) {
            region_iv_first.visibility = View.VISIBLE
            region_tv_first.visibility = View.GONE
            region_close_first.visibility = View.GONE
        } else {
            setViewNeighborhood(region_tv_first, region_iv_first, selectedList.first?.neighborhood)
        }

        if (selectedList.second == null) {
            region_iv_second.visibility = View.VISIBLE
            region_tv_second.visibility = View.GONE
            region_close_second.visibility = View.GONE
        } else {
            setViewNeighborhood(region_tv_second, region_iv_second, selectedList.second?.neighborhood)
        }
    }

    private fun setViewNeighborhood(tv: TextView, iv: ImageView, neighborhood: String?) {
        tv.apply {
            visibility = View.VISIBLE
            text = neighborhood
        }
        iv.visibility = View.GONE
        region_close_second.visibility = View.VISIBLE
    }

    private fun closeViewNeighborhood() {
        if (selectedList.second != null) {
            selectedList = selectedList.copy(selectedList.first, null)
            presenter.saveSelectedLocationList(selectedList.first, selectedList.second)
            region_iv_second.visibility = View.VISIBLE
            region_tv_second.visibility = View.GONE
            region_close_second.visibility = View.GONE
            setViewSelectedNeighborhoodLayout(SELECT_FIRST)
        }
    }


    private fun setViewSelectedNeighborhoodLayout(selected:Int) {
        presenter.setSelectedNumber(selected)
        when(selected) {
            1 -> {
                region_tv_second.background = getDrawable(R.drawable.bg_layout_region)
                region_tv_first.background = getDrawable(R.drawable.bg_layout_region_selected)
                currentPosition = selectedList.first!!
            }
            2 -> {
                region_tv_first.background = getDrawable(R.drawable.bg_layout_region)
                region_tv_second.background = getDrawable(R.drawable.bg_layout_region_selected)
                currentPosition = selectedList.second!!
            }
        }
        presenter.sortRegionList(currentPosition)
        presenter.calNearByRegion(progressCount)
    }

    override fun updateNeighborhoodCount(count: Int) {
        region_tv_neighborhood_count.text = getString(R.string.region_name_count_text, currentPosition.neighborhood, count)
    }
}