package com.study.carrotmarket.constant

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseUpdateUser(
    var nickname: String,
    var e_mail: String,
    var cellphone: String,
    var region: String,
    var regionLevel: Int,
    var profileUri: String,
    var certCnt: Int,
    var mannerTemp: Double,
    var reselRate: Double,
    var msgResponseRate:Double,
    var lastJoin:Int
): Parcelable
