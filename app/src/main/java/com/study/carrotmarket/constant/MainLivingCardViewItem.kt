package com.study.carrotmarket.constant

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MainLivingCardViewItem (
    val cardCategory: Int,
    val cardTimeSamp: Date,
    val cardUserName: String,
    val cardUserAddInfo: String,
    val cardUserComment: String,
    val cardUser: Int
):Parcelable