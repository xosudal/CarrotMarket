package com.study.carrotmarket.presenter.setting

import com.study.carrotmarket.constant.KeywordNotifyContract
import com.study.carrotmarket.model.setting.SettingModel

class KeywordNotifyPresenter : KeywordNotifyContract.Presenter {

    override fun saveKeyword(list:ArrayList<String>) {
        SettingModel.saveKeyword(list)
    }

    override fun loadKeyword():ArrayList<String> {
        return SettingModel.loadKeyword()
    }
}