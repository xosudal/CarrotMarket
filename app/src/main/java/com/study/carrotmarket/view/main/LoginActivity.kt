package com.study.carrotmarket.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.LoginContract
import com.study.carrotmarket.constant.UserRequest
import com.study.carrotmarket.presenter.main.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

class LoginActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var presenter:LoginPresenter
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        presenter = LoginPresenter().apply {
            view = this@LoginActivity
        }
        loadingDialog = LoadingDialog(this)
        settingToolbar()
        email_sign_up_btn.setOnClickListener {
            loadingDialog.show()
            presenter.signUp(email_edit_tv.text.toString(), password_edit_tv.text.toString())
        }

        email_sign_in_btn.setOnClickListener {
            loadingDialog.show()
            presenter.signIn(email_edit_tv.text.toString(), password_edit_tv.text.toString())
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
        toolbar_title.text = "로그인 하기"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    override fun successSignIn() {
        loadingDialog.dismiss()
        finish()
    }

    override fun successSignUp() {
        loadingDialog.dismiss()
        val intent = Intent(this,NicknameSettingActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}