package com.study.carrotmarket.constant

interface LoginContract {
    interface View {
        fun dismissLoadingDialog()
        fun successSignIn()
        fun successSignUp()
        fun showMessage(message:String)
    }

    interface Presenter {
        var view:View
        fun signUp(id:String, password:String)
        fun signIn(id:String, password:String)
    }
}