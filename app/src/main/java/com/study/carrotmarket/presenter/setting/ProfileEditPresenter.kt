package com.study.carrotmarket.presenter.setting

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.study.carrotmarket.constant.ProfileEditContract
import com.study.carrotmarket.constant.UserInfo
import com.study.carrotmarket.model.setting.SettingModel
import kotlinx.android.synthetic.main.activity_profile_edit.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ProfileEditPresenter:ProfileEditContract.Presenter {

    private lateinit var context: Context
    override lateinit var view: ProfileEditContract.View
    private var auth = FirebaseAuth.getInstance()
    private var storage = FirebaseStorage.getInstance()
    private var fireStore = FirebaseFirestore.getInstance()

    override fun loadProfileUri(): String? {
        return SettingModel.loadProfileUri()
    }

    override fun saveProfileUri(uri: String?) {
        SettingModel.saveProfileUri(uri)
    }

    override fun getNickname(): String? {
        return SettingModel.getNickname()
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

    override fun resizePhoto(uri:Uri?): Uri {
        var bitmap: Bitmap? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (uri != null) {
                val src = ImageDecoder.createSource(context.contentResolver,uri)
                bitmap = ImageDecoder.decodeBitmap(src)
            }
        } else {
            bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver,uri)
        }

        val time = System.currentTimeMillis()
        val file = File("${context.filesDir}/images/${time}.png")
        Log.d("heo", file.path)
        val fOut = FileOutputStream(file)
        val resized = bitmap?.let { Bitmap.createScaledBitmap(it,bitmap.width/8,bitmap.height/8,true) }
        resized?.compress(Bitmap.CompressFormat.PNG,100, fOut)
        fOut.flush()

        return Uri.fromFile(file)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun uploadUserInfo(photoUri:Uri?, nickname: String) {
        view.uploadStart()
        val timeStamp = SimpleDateFormat("yyyyMMDD_HHmmss").format(Date())
        val imageFileName = "IMAGE_$timeStamp.png"

        val storageReference = storage.reference.child("ProfileImage").child(imageFileName)

        photoUri?.let {
            storageReference.putFile(it).continueWithTask {
                return@continueWithTask storageReference.downloadUrl
            }
        }?.addOnSuccessListener {
            val userInfo = UserInfo(it.toString(), nickname, auth.currentUser?.uid, auth.currentUser?.email)
            fireStore.collection("ProfileImage").document(userInfo.uid.toString()).set(
                userInfo
            )
            deleteFilesInImageDir()
            setNickname(nickname)
            saveProfileUriFromServer()
        }
    }

    private fun saveProfileUriFromServer() {
        fireStore.collection("ProfileImage").document(auth.currentUser?.uid.toString()).get()
            .addOnSuccessListener {
                if (it == null) return@addOnSuccessListener
                if (it.data != null) {
                    Log.d("heo","save uri : ${it.data!!["imageUri"]}")
                    saveProfileUri(it.data!!["imageUri"].toString())
                    view.uploadEnd()
                }
            }
    }

    private fun deleteFilesInImageDir() {
        val dir = File("${context.filesDir}/images")
        if (dir.exists()) {
            val list = dir.listFiles()
            if (!list.isNullOrEmpty()) {
                for (file in list) {
                    file.delete()
                }
            }
        }
    }
}