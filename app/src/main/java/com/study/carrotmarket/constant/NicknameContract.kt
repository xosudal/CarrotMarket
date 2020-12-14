package com.study.carrotmarket.constant

interface NicknameContract {
    interface View {
        fun dismissLoadingDialog()
    }

    interface Presenter {
        var view:View
        fun setNickname(nickname:String)
    }
}