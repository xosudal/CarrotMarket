package com.study.carrotmarket.constant

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LocationInfo(
    var province: String = "", // 도
    var city: String = "", // 시
    var district: String = "", // 구
    var neighborhood: String = "", // 동
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var distance: Float = 0.0F
) : Parcelable