package com.study.carrotmarket.setting.activity

import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_region_setting.*
import kotlinx.android.synthetic.main.toolbar.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

class RegionSettingActivity : AppCompatActivity() {
    companion object StaticList {
        var regionTotalList:ArrayList<LocationInfo> = arrayListOf()
        lateinit var regionNearByList:List<LocationInfo>

        data class LocationInfo(
            var province:String ="", // 도
            var city:String = "", // 시
            var district:String = "", // 구
            var neighborhood:String = "", // 동
            var latitude:Double = 0.0,
            var longitude:Double = 0.0,
            var distance:Float = 0.0F
        )
    }

    private val currentPosition = LocationInfo("","서울특별시","강서구","가양동",37.5648322,126.8342406)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region_setting)
        settingToolbar()


        region_seek_bar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
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
            startActivity(Intent(this,RegionShowActivity::class.java))
        }

        region_iv_first.setOnClickListener {
            startActivity(Intent(this,RegionActivity::class.java))
        }

        region_iv_second.setOnClickListener {
            startActivity(Intent(this,RegionActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        region_seek_bar.progress = 0
        setImageAlpha(0)
        calNearByRegion(0)
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
        toolbar_title.text = "내 동네 설정"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setImageAlpha(position:Int) {
        when(position) {
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
            Log.d("heo","list already loaded")
            return
        }
        val reader = BufferedReader(InputStreamReader(assets.open("LocationList.txt")))
        var read:String
        for(read in reader.lines()) {
            read.let {
                val jArr = JSONArray(it)
                for (i in 0 until jArr.length()) {
                    val split = jArr.opt(i).toString().split(" ")
                    if (split.isNotEmpty()) {
                        if (split.size == 6) { // 도 시 구 동
                            regionTotalList.add(LocationInfo(split[0],split[1],split[2],split[3],split[4].toDouble(),split[5].toDouble()))
                        } else if (split.size ==5) {
                            if (split[0].endsWith('도')) // 도 시 동
                                regionTotalList.add(LocationInfo(split[0],split[1],"",split[2],split[3].toDouble(),split[4].toDouble()))
                            else if (split[0].endsWith('시'))// 시 구 동
                                regionTotalList.add(LocationInfo("",split[0],split[1],split[2],split[3].toDouble(),split[4].toDouble()))
                        }
                    }
                }
            }
        }

        for (list in regionTotalList) list.distance = betweenDistance(currentPosition.latitude,currentPosition.longitude,list.latitude,list.longitude)
    }

    private fun calNearByRegion(position: Int) {
        var distance:Float =
        when(position) {
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
        Log.d("heo",regionNearByList.size.toString())
        region_tv_neighborhood_count.text = getString(R.string.region_name_count_text,currentPosition.neighborhood,regionNearByList.size)
    }

    private fun betweenDistance(latitude1:Double, longitude1:Double, latitude2: Double, longitude2: Double):Float {
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
}

