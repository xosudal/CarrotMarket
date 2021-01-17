package com.study.carrotmarket.presenter.setting

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.study.carrotmarket.constant.MyCarrotContract
import com.study.carrotmarket.model.setting.SettingModel

class MyCarrotPresenter:MyCarrotContract.Presenter {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun loadProfileUri(): String? {
        return SettingModel.loadProfileUri()
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

    override fun isLogIn(): Boolean {
        return auth.currentUser != null
    }
}