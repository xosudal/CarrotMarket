package com.study.carrotmarket.view.setting.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.ProfileEditContract
import com.study.carrotmarket.constant.UserInfo
import com.study.carrotmarket.presenter.setting.ProfileEditPresenter
import com.study.carrotmarket.view.main.LoadingDialog
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class ProfileEditActivity : AppCompatActivity(), ProfileEditContract.View {
    private val PICK_IMAGE_FROM_ALBUM = 1001
    private lateinit var presenter:ProfileEditPresenter
    private var photoUri: Uri? = null
    private lateinit var dialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)
        settingToolbar()
        presenter = ProfileEditPresenter().apply {
            view = this@ProfileEditActivity
        }

        dialog = LoadingDialog(this)

        profile_edit_iv.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.setContext(this)
        presenter.checkImageDir()
        profile_edit_et.setText(presenter.getNickname())
        showProfileImage()
    }
    private fun showProfileImage() {
        val profileUri = presenter.loadProfileUri() ?: return

        if (profileUri.startsWith("file")) {
            val temp = Uri.parse(profileUri)
            Glide.with(this).load(File(temp.path!!)).diskCacheStrategy(DiskCacheStrategy.ALL).circleCrop().into(profile_edit_iv)
        }
        else {
            Glide.with(this).load(profileUri).circleCrop().into(profile_edit_iv)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                photoUri = presenter.resizePhoto(data?.data)
            }
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
                // TODO
                Log.d("heo","uri : $photoUri")
                presenter.uploadUserInfo(photoUri,profile_edit_et.text.toString())
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

    override fun uploadStart() {
        dialog.show()
    }

    override fun uploadEnd() {
        dialog.dismiss()
        setResult(RESULT_OK)
        finish()
    }
}