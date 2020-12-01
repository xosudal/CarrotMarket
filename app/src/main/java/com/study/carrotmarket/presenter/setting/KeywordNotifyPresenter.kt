package com.study.carrotmarket.presenter.setting

import com.study.carrotmarket.constant.KeywordNotifyContract
import com.study.carrotmarket.model.setting.KeywordNotifyModel

class KeywordNotifyPresenter : KeywordNotifyContract.Presenter {
    override lateinit var model: KeywordNotifyModel

    override fun saveKeyword(list:ArrayList<String>) {
        model.saveKeyword(list)
    }

    override fun loadKeyword():ArrayList<String> {
        return model.loadKeyword()
    }
}