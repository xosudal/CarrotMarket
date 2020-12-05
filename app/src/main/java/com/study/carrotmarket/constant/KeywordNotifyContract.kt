package com.study.carrotmarket.constant

import com.study.carrotmarket.model.setting.KeywordNotifyModel

interface KeywordNotifyContract {
    interface View {
    }

    interface Presenter {
        fun saveKeyword(list:ArrayList<String>)
        fun loadKeyword():ArrayList<String>
    }
}