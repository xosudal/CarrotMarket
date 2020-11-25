package com.study.carrotmarket.model.setting

import android.util.Log
import com.study.carrotmarket.view.setting.activity.KeywordNotifyActivity
import org.json.JSONArray

class KeywordNotifyModel (private val context: KeywordNotifyActivity) {
    private val keywordList = arrayListOf<String>()

    fun saveKeyword() {
        if (keywordList.isEmpty()) return
        val jArr = JSONArray()
        for (i in keywordList) {
            jArr.put(i)
        }
        context.getPreferences(0).edit().putString("LIST",jArr.toString()).apply()
    }

    fun loadKeyword() {
        val loadStr : String? = context.getPreferences(0).getString("LIST", null)
        loadStr?.let {
            val jArr = JSONArray(it)
            for (i in 0 until jArr.length()) {
                keywordList.add(jArr.opt(i).toString())
            }
        }
        Log.d("heo", keywordList.toString())
    }

    fun getSize():Int {
        return keywordList.size
    }

    fun addKeyword(keyword:String) {
        keywordList.add(keyword)
        Log.d("heo", "add keyword : $keywordList")
    }
}