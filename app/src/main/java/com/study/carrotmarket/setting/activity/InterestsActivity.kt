package com.study.carrotmarket.setting.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_interests.*

class InterestsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interests)

        interest_btn_back.setOnClickListener {
            finish()
        }
    }
}