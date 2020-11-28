package com.study.carrotmarket.constant

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailUsedItemResponse(val id : Int,
                            val nickname : String?,
                            val region : String?,
                            val price : Int,
                            val images : Int,
                            val title : String?,
                            val desc : String?,
                            val category: String?,
                            val manner_temp : Float,
                            val last_modified : Int,
                            val chat_count : Int,
                            val interest_cnt : Int,
                            val hit_cnt : Int,
                            val reported_cnt : Int) : Parcelable