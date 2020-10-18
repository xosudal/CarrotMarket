package com.study.carrotmarket.setting.activity

import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.study.carrotmarket.R
import com.study.carrotmarket.setting.model.LocationInfo
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_region_setting.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

const val SELECT_FIRST = 1
const val SELECT_SECOND = 2
class RegionSettingActivity : AppCompatActivity() {

    companion object StaticList {
        var regionTotalList: ArrayList<LocationInfo> = arrayListOf()
        lateinit var regionNearByList: List<LocationInfo>
    }

    private lateinit var currentPosition:LocationInfo
    private var selectedFirstLocation:LocationInfo? = LocationInfo("", "서울특별시", "강서구", "가양동", 37.5648322, 126.8342406)
    private var selectedSecondLocation:LocationInfo? = null
    private var progressCount:Int = 0
    private var selectedNumber:Int = 1

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region_setting)
        settingToolbar()


        region_seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                progressCount = p1
                getPreferences(0).edit().putInt("PROGRESS",progressCount).apply()
                setImageAlpha(p1)
                calNearByRegion(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        loadRegionList()

        region_tv_neighborhood_count.setOnClickListener {
            startActivity(Intent(this, RegionShowActivity::class.java))
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
                    selectedFirstLocation = data?.getParcelableExtra("selectList")
                    selectedFirstLocation?.let { setSelectedNeighborhood(it) }
                }
            }

            SELECT_SECOND -> {
                if (resultCode == RESULT_OK) {
                    selectedSecondLocation = data?.getParcelableExtra("selectList")
                    selectedSecondLocation?.let { setSelectedNeighborhood(it) }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        progressCount = getPreferences(0).getInt("PROGRESS",0)
        selectedNumber = getPreferences(0).getInt("SELECTED_NUMBER",1)
        loadSelectedLocationList()
        setViewSelectedNeighborhoodLayout(selectedNumber)
        region_seek_bar.progress = progressCount
        setImageAlpha(progressCount)
        calNearByRegion(progressCount)
        loadSelectedNeighborhood()
        sortRegionList(currentPosition)
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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadRegionList() {
        if (regionTotalList.size > 0) {
            Log.d("heo", "list already loaded")
            return
        }
        val reader = BufferedReader(InputStreamReader(assets.open("LocationList.txt")))
        for (read in reader.lines()) {
            read.let {
                val jArr = JSONArray(it)
                for (i in 0 until jArr.length()) {
                    val split = jArr.opt(i).toString().split(" ")
                    if (split.isNotEmpty()) {
                        if (split.size == 6) { // 도 시 구 동
                            regionTotalList.add(
                                LocationInfo(
                                    split[0],
                                    split[1],
                                    split[2],
                                    split[3],
                                    split[4].toDouble(),
                                    split[5].toDouble()
                                )
                            )
                        } else if (split.size == 5) {
                            if (split[0].endsWith('도')) // 도 시 동
                                regionTotalList.add(
                                    LocationInfo(
                                        split[0],
                                        split[1],
                                        "",
                                        split[2],
                                        split[3].toDouble(),
                                        split[4].toDouble()
                                    )
                                )
                            else if (split[0].endsWith('시'))// 시 구 동
                                regionTotalList.add(
                                    LocationInfo(
                                        "",
                                        split[0],
                                        split[1],
                                        split[2],
                                        split[3].toDouble(),
                                        split[4].toDouble()
                                    )
                                )
                        }
                    }
                }
            }
        }
    }

    private fun sortRegionList(currentPosition:LocationInfo) {
        for (list in regionTotalList) list.distance = betweenDistance(
            currentPosition.latitude,
            currentPosition.longitude,
            list.latitude,
            list.longitude
        )
    }

    private fun calNearByRegion(position: Int) {
        val distance: Float =
            when (position) {
                0 -> 3000.0F
                1 -> 5000.0F
                2 -> 7000.0F
                3 -> 10000.0F
                else -> 0.0F
            }

        regionNearByList = regionTotalList.filter {
            it.distance < distance
        }.sortedBy {
            it.distance
        }
        region_tv_neighborhood_count.text = getString(
            R.string.region_name_count_text,
            currentPosition.neighborhood,
            regionNearByList.size
        )
    }

    private fun betweenDistance(
        latitude1: Double,
        longitude1: Double,
        latitude2: Double,
        longitude2: Double
    ): Float {
        val standard = Location("Standard").apply {
            latitude = latitude1
            longitude = longitude1
        }
        val comparison = Location("Comparison").apply {
            latitude = latitude2
            longitude = longitude2
        }
        return standard.distanceTo(comparison)
    }

    private fun setSelectedNeighborhood(list:LocationInfo) {
        if (selectedFirstLocation == null) {
            selectedFirstLocation = list
        } else {
            selectedSecondLocation= list
            setViewNeighborhood(region_tv_second, region_iv_second, selectedSecondLocation?.neighborhood)
        }
        setViewNeighborhood(region_tv_first, region_iv_first, selectedFirstLocation?.neighborhood)
        saveSelectedLocationList()
    }


    private fun loadSelectedNeighborhood() {
        if (selectedFirstLocation == null) {
            region_iv_first.visibility = View.VISIBLE
            region_tv_first.visibility = View.GONE
            region_close_first.visibility = View.GONE
        } else {
            setViewNeighborhood(region_tv_first, region_iv_first, selectedFirstLocation?.neighborhood)
        }

        if (selectedSecondLocation == null) {
            region_iv_second.visibility = View.VISIBLE
            region_tv_second.visibility = View.GONE
            region_close_second.visibility = View.GONE
        } else {
            setViewNeighborhood(region_tv_second, region_iv_second, selectedSecondLocation?.neighborhood)
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
        if (selectedSecondLocation != null) {
            selectedSecondLocation = null
            saveSelectedLocationList()
            region_iv_second.visibility = View.VISIBLE
            region_tv_second.visibility = View.GONE
            region_close_second.visibility = View.GONE
            setViewSelectedNeighborhoodLayout(SELECT_FIRST)
        }
    }

    private fun setViewSelectedNeighborhoodLayout(selected:Int) {
        getPreferences(0).edit().putInt("SELECTED_NUMBER",selected).apply()
        when(selected) {
            1 -> {
                region_tv_second.background = getDrawable(R.drawable.bg_layout_region)
                region_tv_first.background = getDrawable(R.drawable.bg_layout_region_selected)
                currentPosition = selectedFirstLocation!!

            }
            2 -> {
                region_tv_first.background = getDrawable(R.drawable.bg_layout_region)
                region_tv_second.background = getDrawable(R.drawable.bg_layout_region_selected)
                currentPosition = selectedSecondLocation!!
            }
        }
        sortRegionList(currentPosition)
        calNearByRegion(progressCount)
    }

    private fun loadSelectedLocationList() {
        val loadFirst : String? = this.getPreferences(0).getString("FIRST_LIST", null)
        val loadSecond : String? = this.getPreferences(0).getString("SECOND_LIST", null)
        selectedFirstLocation = Gson().fromJson(loadFirst,LocationInfo::class.java) ?: LocationInfo("", "서울특별시", "강서구", "가양동", 37.5648322, 126.8342406)
        selectedSecondLocation = Gson().fromJson(loadSecond,LocationInfo::class.java)
    }

    private fun saveSelectedLocationList() {
        val first:String? = Gson().toJson(selectedFirstLocation)
        val second:String? = Gson().toJson(selectedSecondLocation)
        this.getPreferences(0).edit().putString("FIRST_LIST",first).apply()
        this.getPreferences(0).edit().putString("SECOND_LIST",second).apply()
    }

}

