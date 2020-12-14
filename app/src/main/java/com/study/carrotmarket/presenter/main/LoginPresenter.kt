package com.study.carrotmarket.presenter.main

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.study.carrotmarket.constant.LoginContract

class LoginPresenter:LoginContract.Presenter {
    override lateinit var view: LoginContract.View
    private val passwordLength = 6
    private var auth = FirebaseAuth.getInstance()

    override fun signUp(id: String, password: String) {
        if (password.length < passwordLength) {
            view.showMessage("Password length must be at least 6")
            view.dismissLoadingDialog()
            return
        }

        auth.createUserWithEmailAndPassword(id, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("heo", "signUp Success")
                view.successSignUp()
            } else {
                Log.d("heo", "signUp Fail")
                if (it.exception is FirebaseAuthUserCollisionException) {
                    view.showMessage("Account already exists!")
                    view.dismissLoadingDialog()
                } else {
                    view.showMessage(it.exception.toString())
                    view.dismissLoadingDialog()
                }
            }
        }
    }

    override fun signIn(id: String, password: String) {
        auth.signInWithEmailAndPassword(id, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("heo","Login Success")
                view.successSignIn()
            } else {
                Log.d("heo","Login Fail")
                view.dismissLoadingDialog()
                view.showMessage(it.exception.toString())
            }
        }
    }

}