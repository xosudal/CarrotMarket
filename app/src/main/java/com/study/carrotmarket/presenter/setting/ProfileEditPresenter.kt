package com.study.carrotmarket.presenter.setting

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.gson.Gson
import com.study.carrotmarket.constant.ProfileEditContract
import com.study.carrotmarket.constant.UserRequest
import com.study.carrotmarket.model.CarrotMarketDataRepository
import com.study.carrotmarket.model.setting.SettingModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


class ProfileEditPresenter:ProfileEditContract.Presenter {

    private lateinit var context: Context
    override lateinit var view: ProfileEditContract.View
    private var auth = FirebaseAuth.getInstance()

    override fun loadProfileUri(): Uri? {
        return auth.currentUser?.photoUrl
    }

    override fun saveProfileUri(uri: String?) {
        SettingModel.saveProfileUri(uri)
    }

    override fun getNickname(): String? {
        return auth.currentUser?.displayName
    }

    override fun setNickname(nickname: String) {
        SettingModel.setNickname(nickname)
    }

    override fun setContext(context: Context) {
        this.context = context
        SettingModel.setContext(context)
    }

    override fun checkImageDir() {
        val dir = File("${context.filesDir}/images")
        if (!dir.exists()) {
            dir.mkdir()
        }
    }

    override fun resizePhoto(uri: Uri?): File {
        var bitmap: Bitmap? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (uri != null) {
                val src = ImageDecoder.createSource(context.contentResolver, uri)
                bitmap = ImageDecoder.decodeBitmap(src)
            }
        } else {
            bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }

        val time = System.currentTimeMillis()
        val file = File("${context.filesDir}/images/${time}.png")
        val fOut = FileOutputStream(file)
        val resized = bitmap?.let { Bitmap.createScaledBitmap(
            it,
            bitmap.width / 8,
            bitmap.height / 8,
            true
        ) }
        resized?.compress(Bitmap.CompressFormat.PNG, 100, fOut)
        fOut.flush()

        return file
    }

    override fun uploadUserInfo(file: File?, nickname: String) {
        view.uploadStart()

        val update:UserProfileChangeRequest = if (file!= null) {
            UserProfileChangeRequest.Builder()
                .setDisplayName(nickname)
                .setPhotoUri(Uri.fromFile(file))
                .build()
        } else {
            UserProfileChangeRequest.Builder()
                .setDisplayName(nickname)
                .build()
        }
        auth.currentUser?.updateProfile(update)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("heo", "update success!")
                    Log.d("heo","result : ${auth.currentUser?.photoUrl}, ${auth.currentUser?.displayName}")
                    view.uploadEnd()
                } else {
                    Log.d("heo", "update fail!")
                    view.uploadEnd()
                }
            }
        sendProfileUriToServer(file)
    }

    private fun deleteFilesInImageDir(fileName:String) {
        val dir = File("${context.filesDir}/images")
        if (dir.exists()) {
            val list = dir.listFiles()
            if (!list.isNullOrEmpty()) {
                for (file in list) {
                    if (fileName != file.name)
                        file.delete()
                }
            }
        }
    }

    private fun sendProfileUriToServer(file: File?) {
        val body = MultipartBody.Builder()
        val user = auth.currentUser
        val nickname:String = user?.displayName ?: ""
        val email:String = user?.email ?: ""
        val fileName:String = file?.name ?: ""

        val userRequest = UserRequest(
            "Clozer",
            email,
            "010-1234-5678",
            "가양동",
            3,
            fileName
        )
        val jsonUserRequest = Gson().toJson(userRequest)

        body.addFormDataPart("data", jsonUserRequest)

        val requestBody = file?.asRequestBody("image/*".toMediaTypeOrNull())
        if (requestBody != null) {
            body.addFormDataPart("item_${fileName}", "item_${fileName}.png", requestBody)
        }

        val dispose = CarrotMarketDataRepository.sendUserInfo(body.build().parts)
            .subscribe({
                Log.d("test","[subscribe] success!! photo Uri : ${it[0].profileUri}")
                if (file != null) {
                    deleteFilesInImageDir(file.name)
                }
            }, {
                Log.d("test", "fail : $it")
            })
    }
}