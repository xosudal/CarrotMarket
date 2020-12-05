package com.study.carrotmarket.view.main

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.UserRequest
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

class LoginActivity : AppCompatActivity() {
    companion object {
        private const val PASSWORD_LENGTH = 6
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loadingDialog = LoadingDialog(this)
        auth = FirebaseAuth.getInstance()
        settingToolbar()
        email_sign_up_btn.setOnClickListener {
            loadingDialog.show()
            signUp()
        }

        email_sign_in_btn.setOnClickListener {
            loadingDialog.show()
            signIn()
        }
    }

    private fun signUp() {
        if (password_edit_tv.text.length < PASSWORD_LENGTH) {
            Toast.makeText(this,"Password length must be at least 6",Toast.LENGTH_SHORT).show()
            loadingDialog.dismiss()
            return
        }

        auth.createUserWithEmailAndPassword(email_edit_tv.text.toString(), password_edit_tv.text.toString()).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("heo", "signUp Success")
            } else {
                Log.d("heo", "signUp Fail")
                if (it.exception is FirebaseAuthUserCollisionException) {
                    Toast.makeText(this, "Account already exists!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            loadingDialog.dismiss()
        }
    }

    private fun signIn() {
        auth.signInWithEmailAndPassword(email_edit_tv.text.toString(), password_edit_tv.text.toString()).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("heo","Login Success")
                loadingDialog.dismiss()
                finish()
            } else {
                Log.d("heo","Login Fail")
                loadingDialog.dismiss()
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
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
}