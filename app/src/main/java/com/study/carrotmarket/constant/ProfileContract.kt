package com.study.carrotmarket.constant

import android.content.Context
import android.net.Uri

interface ProfileContract {
    interface View {

    }

    interface Presenter {
        fun loadProfileUri(): Uri?
        fun saveProfileUri(uri:String?)
        fun setContext(context: Context)
        fun getUserID():String?
        fun getUserRegion():String
    }
}