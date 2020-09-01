package com.study.carrotmarket.setting.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_purchase.*

class PurchaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_purchase)
        purchase_btn_back.setOnClickListener {
            finish()
        }
    }
}