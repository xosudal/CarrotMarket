package com.study.carrotmarket.constant

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SettingPreference(
    var majorAlarmIsChecked: Boolean = false,
    var minorAlarmIsChecked: Boolean = false,
    var vibrateIsChecked: Boolean = false,
    var disturbIsChecked: Boolean = false,
    var alarmSoundIndex: Int = 0,
    var language: Int = 1
) : Parcelable
