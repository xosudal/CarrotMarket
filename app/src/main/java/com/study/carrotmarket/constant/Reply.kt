package com.study.carrotmarket.constant

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Reply (
    var nickname:String,
    var mainText:String,
    var parentIndex:Int,
    var childIndex:Int
):Parcelable