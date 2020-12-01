package com.study.carrotmarket.model.setting

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.study.carrotmarket.constant.LocationInfo
import com.study.carrotmarket.view.setting.activity.RegionSettingActivity
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

object RegionListModel {
    var regionBaseList:ArrayList<LocationInfo> = arrayListOf()
    var regionList:List<LocationInfo> = listOf()
    private lateinit var context: Context

    fun setContext(context: Context) {
        this.context = context
    }

    fun createRegionList() {
        regionList = regionBaseList.sortedBy {
            it.distance
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadRegionList() {
        if (regionBaseList.isNotEmpty()) {
            Log.d("heo", "list already loaded")
            return
        }

        val reader = BufferedReader(InputStreamReader(context.resources.assets.open("LocationList.txt")))
        for (read in reader.lines()) {
            read.let {
                val jArr = JSONArray(it)
                for (i in 0 until jArr.length()) {
                    val split = jArr.opt(i).toString().split(" ")
                    if (split.isNotEmpty()) {
                        if (split.size == 6) { // 도 시 구 동
                            regionBaseList.add(
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
                                regionBaseList.add(
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
                                regionBaseList.add(
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
}