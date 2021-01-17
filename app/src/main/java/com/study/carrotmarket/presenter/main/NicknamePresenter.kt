package com.study.carrotmarket.presenter.main

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.study.carrotmarket.constant.NicknameContract
import com.study.carrotmarket.constant.UserRequest
import com.study.carrotmarket.model.RestApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class NicknamePresenter:NicknameContract.Presenter {
    private val auth = FirebaseAuth.getInstance()
    override lateinit var view: NicknameContract.View

    override fun setNickname(nickname: String) {
        //val userRequest = UserRequest(nickname, auth.currentUser?.email ?: "no ID","010-1234-5678")
        //sendUserRequest(userRequest)
    }

    private fun sendUserRequest(userRequest: UserRequest) {
        Log.d("test","sendUserRequest")
        val mediaType = "application/json".toMediaTypeOrNull()
        val json = Gson().toJson(userRequest)
        Log.d("test",json.toString())
        val body = json.toRequestBody(mediaType)
    }
}