package com.study.carrotmarket

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_neighborhood.*
import java.util.*

class NeighborhoodFragment : Fragment() {
    private val TAG: String = "Neighborhood"
    private val recommandList = listOf("원데이트 클래스", "배송가능 용달", "인테리어 서비스", "가볼만한 까페", "머리 잘하는 곳", "저렴한 네일샵")
    private val recommandStoreList = listOf("클래스", "속눈썹", "이사", "용달", "네일", "공방", "인테리어", "에어컨", "컴퓨터")
    private lateinit var mRandom : Random
    private lateinit var mRunnable : Runnable
    private lateinit var mHandler : Handler
    private val REPEAT_TIME : Long = 2000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_neighborhood, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mHandler = Handler()
        mRandom = Random()
        mRunnable = Runnable {
            val randomRecommand = recommandList[mRandom.nextInt(recommandList.size)]
            val villagewholeStr = view.resources.getString(R.string.my_village, "화이트푸", "도화동", randomRecommand)
            showRecommandVillageService(villagewholeStr, randomRecommand).let {
                lottie_tv?.setText(it)
            }
            mHandler.postDelayed(mRunnable, REPEAT_TIME)
        }
        mHandler.post(mRunnable)

        for(storelist in recommandStoreList) {
            val chip = LayoutInflater.from(view.context).inflate(R.layout.layout_chip_action, chip_group, false) as Chip
            chip.setText(storelist)
            chip.setOnClickListener {
                Toast.makeText(
                    it.context,
                    "${chip.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            chip_group.addView(chip)
        }

        /* What about */
        val whatAboutDataSet = ArrayList<WhatAboutItem>()
        whatAboutDataSet.add(WhatAboutItem(R.drawable.ic_twotone_job_recruit_24, "동네 구인구직"))
        whatAboutDataSet.add(WhatAboutItem(R.drawable.ic_twotone_event_lesson_24, "과외/클래스 모집"))
        whatAboutDataSet.add(WhatAboutItem(R.drawable.ic_twotone_product_24, "농수산물"))
        whatAboutDataSet.add(WhatAboutItem(R.drawable.ic_twotone_land_24, "부동산"))
        whatAboutDataSet.add(WhatAboutItem(R.drawable.ic_twotone_directions_car_24, "중고차/오토바이"))
        whatAboutDataSet.add(WhatAboutItem(R.drawable.ic_twotone_event_24, "전지/공연/행사"))

        val whatAboutAdapter = WhatAboutAdapter(whatAboutDataSet)
        what_about_recyclerview.layoutManager = GridLayoutManager(view.context, 4)
        what_about_recyclerview.adapter = whatAboutAdapter

        /* Recommand store by neighborhood */
        val recommandStoreDataSet = ArrayList<RecommandStoreItem>()
        recommandStoreDataSet.add(RecommandStoreItem(R.drawable.maka, R.drawable.maka2, "HZAC", "용강동", "어린이 미술 교육", "후기 관심", "봄tiful님 퇴근 후, 저녁 시간을 좀 더 유익하게..."))
        recommandStoreDataSet.add(RecommandStoreItem(R.drawable.maka, R.drawable.maka2, "엘린뷰티", "용강동", "어린이 미술 교육", "후기 관심", "봄tiful님 퇴근 후, 저녁 시간을 좀 더 유익하게..."))
        recommandStoreDataSet.add(RecommandStoreItem(R.drawable.maka, R.drawable.maka2, "녹기전에", "용강동", "어린이 미술 교육", "후기 관심", "봄tiful님 퇴근 후, 저녁 시간을 좀 더 유익하게..."))
        recommandStoreDataSet.add(RecommandStoreItem(R.drawable.maka, R.drawable.maka2, "소울헤어", "용강동", "어린이 미술 교육", "후기 관심", "봄tiful님 퇴근 후, 저녁 시간을 좀 더 유익하게..."))
        val recommandStoreAdapter = RecommandStoreAdapter(recommandStoreDataSet)
        recommand_store_recyclerview.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        recommand_store_recyclerview.adapter = recommandStoreAdapter
    }

    private fun showRecommandVillageService(wholeString : String, keyword : String) : SpannableString {
        Log.d(TAG, "whole: $wholeString, keyword: $keyword")
        val span = SpannableString(wholeString)

        val start = wholeString.indexOf(keyword)
        val end = start + keyword.length

        span.setSpan(ForegroundColorSpan(Color.parseColor("#FF5E13")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        span.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

        return span
    }
}
