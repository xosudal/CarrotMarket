package com.study.carrotmarket.setting.activity

import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.study.carrotmarket.LoadingDialog
import com.study.carrotmarket.R
import com.study.carrotmarket.model.UserInfo
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class ProfileEditActivity : AppCompatActivity() {
    private val PICK_IMAGE_FROM_ALBUM = 1001
    var photoUri: Uri? = null
    var nickName: String? = null

    lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage
    lateinit var firestore: FirebaseFirestore
    var userInfo = UserInfo()

    lateinit var dialog:LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
        settingToolbar()
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        firestore = FirebaseFirestore.getInstance()
        dialog = LoadingDialog(this)

        profile_edit_iv.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)
        }
        userInfo = intent.getParcelableExtra("USER_INFO") ?: UserInfo()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                photoUri = data?.data
                userInfo.imageUri = data?.data.toString()
                Log.d("heo","onActivityResult in edit ac")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //load uri, nickname and set
        profile_edit_et.setText(userInfo.nickName)
        Glide.with(this).asBitmap().load(userInfo.imageUri).circleCrop().into(profile_edit_iv)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun uploadImageAndNickName() {
        dialog.show()
        val timeStamp = SimpleDateFormat("yyyyMMDD_HHmmss").format(Date())
        val imageFileName = "IMAGE_$timeStamp.png"

        val storageReference = storage.reference.child("ProfileImage").child(imageFileName)

        photoUri?.let {
            storageReference.putFile(it).continueWithTask {
                return@continueWithTask storageReference.downloadUrl
            }
        }?.addOnSuccessListener {
            userInfo.apply {
                imageUri = it.toString()
                uid = auth.currentUser?.uid
                userId = auth.currentUser?.email
                nickName = profile_edit_et.text.toString()
            }
            userInfo.let { info ->
                firestore.collection("ProfileImage").document(userInfo.uid.toString()).set(
                    info
                )
            }
            dialog.dismiss()
            val intent = Intent().putExtra("USER_INFO",userInfo)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_profile_edit_complete -> {
                // save
                uploadImageAndNickName()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_profile_edit, menu)
        return true
    }

    private fun settingToolbar() {
        setSupportActionBar(toolbar).apply {
            title = null
        }
        toolbar_title.text = "프로필 설정"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}