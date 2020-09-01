package com.study.carrotmarket.setting.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_following_article.*

class FollowingArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_following_article)

        following_article_btn_back.setOnClickListener {
            finish()
        }
    }
}