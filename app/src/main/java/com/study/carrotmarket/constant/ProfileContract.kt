package com.study.carrotmarket.constant

import android.content.Context

interface ProfileContract {
    interface View {

    }

    interface Presenter {
        fun loadProfileUri():String?
        fun saveProfileUri(uri:String?)
        fun setContext(context: Context)
        fun getUserID():String?
        fun getUserRegion():String
    }
}