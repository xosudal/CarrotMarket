package com.study.carrotmarket.presenter.setting

import com.study.carrotmarket.constant.KeywordNotifyContract
import com.study.carrotmarket.model.setting.KeywordNotifyModel

class KeywordNotifyPresenter : KeywordNotifyContract.Presenter {
    override lateinit var model: KeywordNotifyModel

    override fun saveKeyword() {
        model.saveKeyword()
    }

    override fun loadKeyword() {
        model.loadKeyword()
    }

    override fun getSize(): Int {
        return model.getSize()
    }

    override fun addKeyword(keyword: String) {
        model.addKeyword(keyword)
    }
}