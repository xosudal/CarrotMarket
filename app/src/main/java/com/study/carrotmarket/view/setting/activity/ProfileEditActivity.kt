package com.study.carrotmarket.view.setting.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.ProfileEditContract
import com.study.carrotmarket.presenter.setting.ProfileEditPresenter
import com.study.carrotmarket.view.main.LoadingDialog
import kotlinx.android.synthetic.main.activity_profile_edit.*
import kotlinx.android.synthetic.main.toolbar.*
import java.io.File

class ProfileEditActivity : AppCompatActivity(), ProfileEditContract.View {
    private val PICK_IMAGE_FROM_ALBUM = 1001
    private lateinit var presenter:ProfileEditPresenter
    private var file: File? = null
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
        if (file == null) {
            Glide.with(this).load(presenter.loadProfileUri()).diskCacheStrategy(DiskCacheStrategy.ALL).circleCrop().into(profile_edit_iv)
        } else {
            Glide.with(this).load(file).circleCrop().into(profile_edit_iv)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                file = presenter.resizePhoto(data?.data)
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
                presenter.uploadUserInfo(file, profile_edit_et.text.toString())
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