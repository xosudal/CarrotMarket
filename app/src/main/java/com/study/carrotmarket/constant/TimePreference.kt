package com.study.carrotmarket.constant

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimePreference (
    var fromHour: Int = 23,
    var fromMinute: Int = 0,
    var toHour: Int = 8,
    var toMinute : Int = 0
) : Parcelable