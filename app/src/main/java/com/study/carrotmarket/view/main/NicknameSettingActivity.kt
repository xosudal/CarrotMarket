package com.study.carrotmarket.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.NicknameContract
import com.study.carrotmarket.presenter.main.NicknamePresenter
import kotlinx.android.synthetic.main.activity_nickname_setting.*
import kotlinx.android.synthetic.main.toolbar.*

class NicknameSettingActivity : AppCompatActivity(), NicknameContract.View {
    private lateinit var presenter:NicknamePresenter
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nickname_setting)
        settingToolbar()
        loadingDialog = LoadingDialog(this)
        presenter = NicknamePresenter().apply {
            view = this@NicknameSettingActivity
        }

        nickname_setting_btn.setOnClickListener {
            loadingDialog.show()
            presenter.setNickname(nickname_edit_tv.toString())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun settingToolbar() {
        setSupportActionBar(toolbar).apply {
            title = null
        }
        toolbar_title.text = "닉네임 설정"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun dismissLoadingDialog() {
        loadingDialog.dismiss()
        finish()
    }
}