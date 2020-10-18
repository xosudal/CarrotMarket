package com.study.carrotmarket.setting.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo (
    var imageUri: String? = null,
    var nickName:String? = null,
    var uid:String? = null,
    var userId:String? = null
): Parcelable