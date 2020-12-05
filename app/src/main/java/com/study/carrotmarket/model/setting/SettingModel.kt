package com.study.carrotmarket.model.setting

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.study.carrotmarket.constant.LocationInfo
import com.study.carrotmarket.view.setting.activity.RegionSettingActivity
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

object SettingModel {
    var regionBaseList:ArrayList<LocationInfo> = arrayListOf()
    var regionList:List<LocationInfo> = listOf()
    private val keywordList = arrayListOf<String>()
    private lateinit var context: Context

    fun setContext(context: Context) {
        this.context = context
    }

    fun createRegionList() {
        regionList = regionBaseList.sortedBy {
            it.distance
        }
    }
    fun setProgressCount(progress:Int) {
        context.getSharedPreferences("PROGRESS", Context.MODE_PRIVATE).edit().putInt("PROGRESS",progress).apply()
    }

    fun getProgressCount():Int {
        return context.getSharedPreferences("PROGRESS", Context.MODE_PRIVATE).getInt("PROGRESS",0)
    }
    fun setSelectedNumber(num:Int) {
        context.getSharedPreferences("SELECTED_NUMBER", Context.MODE_PRIVATE).edit().putInt("SELECTED_NUMBER",num).apply()
    }
    fun getSelectedNumber():Int {
        return context.getSharedPreferences("SELECTED_NUMBER", Context.MODE_PRIVATE).getInt("SELECTED_NUMBER",1)
    }

    fun loadSelectedLocationList():Pair<LocationInfo?,LocationInfo?> {
        val loadFirst : String? = context.getSharedPreferences("FIRST_LIST", Context.MODE_PRIVATE).getString("FIRST_LIST", null)
        val loadSecond : String? = context.getSharedPreferences("SECOND_LIST", Context.MODE_PRIVATE).getString("SECOND_LIST", null)
        val selectedFirstLocation = Gson().fromJson(loadFirst, LocationInfo::class.java) ?: LocationInfo("", "서울특별시", "강서구", "가양동", 37.5648322, 126.8342406)
        val selectedSecondLocation = Gson().fromJson(loadSecond, LocationInfo::class.java)
        return Pair(selectedFirstLocation, selectedSecondLocation)
    }

    fun saveSelectedLocationList(selectedFirstLocation:LocationInfo?, selectedSecondLocation:LocationInfo?) {
        val first:String? = Gson().toJson(selectedFirstLocation)
        val second:String? = Gson().toJson(selectedSecondLocation)
        context.getSharedPreferences("FIRST_LIST", Context.MODE_PRIVATE).edit().putString("FIRST_LIST", first).apply()
        context.getSharedPreferences("SECOND_LIST", Context.MODE_PRIVATE).edit().putString("SECOND_LIST", second).apply()
    }

    fun saveKeyword(list:ArrayList<String>) {
        keywordList.clear()
        keywordList.addAll(list)
        Log.d("test",keywordList.toString())
        if (keywordList.isEmpty()) return
        val jArr = JSONArray()
        for (i in keywordList) {
            jArr.put(i)
        }
        context.getSharedPreferences("LIST", Context.MODE_PRIVATE).edit().putString("LIST",jArr.toString()).apply()
    }

    fun loadKeyword():ArrayList<String> {
        val loadStr : String? = context.getSharedPreferences("LIST", Context.MODE_PRIVATE).getString("LIST", null)
        loadStr?.let {
            val jArr = JSONArray(it)
            for (i in 0 until jArr.length()) {
                keywordList.add(jArr.opt(i).toString())
            }
        }
        Log.d("heo", keywordList.toString())
        return keywordList
    }

    fun loadProfileUri():String? {
        return context.getSharedPreferences("PROFILE", Context.MODE_PRIVATE).getString("PROFILE", null)
    }

    fun saveProfileUri(uri:String?) {
        context.getSharedPreferences("PROFILE", Context.MODE_PRIVATE).edit().putString("PROFILE", uri).apply()
    }

    fun getNickname():String? {
        return context.getSharedPreferences("NICKNAME", Context.MODE_PRIVATE).getString("NICKNAME", null)
    }

    fun setNickname(nickname:String) {
        context.getSharedPreferences("NICKNAME", Context.MODE_PRIVATE).edit().putString("NICKNAME", nickname).apply()
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