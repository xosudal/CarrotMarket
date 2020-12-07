package com.study.carrotmarket.presenter.setting

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.study.carrotmarket.constant.SettingContract
import com.study.carrotmarket.constant.SettingPreference
import com.study.carrotmarket.constant.TimePreference
import com.study.carrotmarket.model.setting.SettingModel

class SettingPresenter : SettingContract.Presenter {
    override lateinit var view: SettingContract.View
    override lateinit var settingPreference: SettingPreference
    override lateinit var timePreference: TimePreference
    private lateinit var context:Context

    override fun setContext(context: Context) {
        this.context = context
        SettingModel.setContext(context)
    }

    override fun savePreference() {
        SettingModel.saveSettingPreference(settingPreference)
        SettingModel.saveTimePreference(timePreference)
    }

    override fun loadPreference() {
        settingPreference = SettingModel.getSettingPreference()
        timePreference = SettingModel.getTimePreference()
    }

    override fun clearCacheByUser() {
        if (context.cacheDir.delete()) {
            view.showDeleteSuccess()
        } else {
            view.showDeleteFail()
        }
    }

    override fun logOut() {
        FirebaseAuth.getInstance().signOut()
    }

    override fun getAlarmSoundList(): Array<String> {
        return SettingModel.getAlarmSoundList()
    }

    override fun getLanguageList(): Array<String> {
        return SettingModel.getLanguageList()
    }
}

