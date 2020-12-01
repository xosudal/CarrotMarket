package com.study.carrotmarket.constant

import com.study.carrotmarket.model.setting.KeywordNotifyModel

interface KeywordNotifyContract {
    interface View {
    }

    interface Presenter {
        var model:KeywordNotifyModel
        fun saveKeyword(list:ArrayList<String>)
        fun loadKeyword():ArrayList<String>
    }
}