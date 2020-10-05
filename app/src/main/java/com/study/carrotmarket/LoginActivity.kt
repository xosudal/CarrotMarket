package com.study.carrotmarket

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    companion object {
        private const val PASSWORD_LENGTH = 6
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var loadingDialog:LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loadingDialog = LoadingDialog(this)
        auth = FirebaseAuth.getInstance()

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
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
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
}