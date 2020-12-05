package com.study.carrotmarket.constant

import android.content.Context
import android.net.Uri

interface ProfileEditContract {
    interface View {
        fun uploadStart()
        fun uploadEnd()
    }

    interface Presenter {
        var view:View

        fun loadProfileUri():String?
        fun saveProfileUri(uri:String?)
        fun getNickname():String?
        fun setNickname(nickname:String)
        fun setContext(context: Context)
        fun checkImageDir()
        fun resizePhoto(uri:Uri?): Uri
        fun uploadUserInfo(photoUri:Uri?, nickname: String)
    }
}