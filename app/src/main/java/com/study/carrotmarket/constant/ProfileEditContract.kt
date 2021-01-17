package com.study.carrotmarket.constant

import android.content.Context
import android.net.Uri
import java.io.File

interface ProfileEditContract {
    interface View {
        fun uploadStart()
        fun uploadEnd()
    }

    interface Presenter {
        var view:View

        fun loadProfileUri():Uri?
        fun saveProfileUri(uri:String?)
        fun getNickname():String?
        fun setNickname(nickname:String)
        fun setContext(context: Context)
        fun checkImageDir()
        fun resizePhoto(uri:Uri?): File
        fun uploadUserInfo(file:File?, nickname: String)
    }
}