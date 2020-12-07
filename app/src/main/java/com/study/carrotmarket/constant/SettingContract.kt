package com.study.carrotmarket.constant

import android.content.Context
import com.study.carrotmarket.model.setting.SettingModel

interface SettingContract {
    interface View {
        fun showDeleteSuccess()
        fun showDeleteFail()
    }

    interface Presenter {
        var view:View
        var settingPreference: SettingPreference
        var timePreference: TimePreference

        fun setContext(context: Context)
        fun savePreference()
        fun loadPreference()
        fun clearCacheByUser()
        fun logOut()
        fun getAlarmSoundList():Array<String>
        fun getLanguageList():Array<String>
    }
}