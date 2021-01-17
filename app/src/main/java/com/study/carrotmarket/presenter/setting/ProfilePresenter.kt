package com.study.carrotmarket.presenter.setting

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.study.carrotmarket.constant.ProfileContract
import com.study.carrotmarket.model.setting.SettingModel

class ProfilePresenter:ProfileContract.Presenter {
    val auth = FirebaseAuth.getInstance()

    override fun loadProfileUri(): Uri? {
        return auth.currentUser?.photoUrl
    }

    override fun saveProfileUri(uri: String?) {
        SettingModel.saveProfileUri(uri)
    }

    override fun setContext(context: Context) {
        SettingModel.setContext(context)
    }

    override fun getUserID(): String? {
        return auth.currentUser?.displayName
    }

    override fun getUserRegion(): String {
        return "가양동" // TODO
    }
}