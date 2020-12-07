package com.study.carrotmarket.constant

interface KeywordNotifyContract {
    interface View {
    }

    interface Presenter {
        fun saveKeyword(list:ArrayList<String>)
        fun loadKeyword():ArrayList<String>
    }
}