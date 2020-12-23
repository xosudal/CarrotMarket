package com.study.carrotmarket.view.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.study.carrotmarket.R
import com.study.carrotmarket.view.main.adapter.HorizontalPictureAdapter
import kotlinx.android.synthetic.main.activity_story_edit.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import kotlinx.android.synthetic.main.toolbar.toolbar_title

class StoryEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_edit)
        settingToolbar()

        val adapter = HorizontalPictureAdapter()

        photos_recyclerview.visibility = View.VISIBLE
        photos_recyclerview.adapter = adapter
        photos_recyclerview.layoutManager = LinearLayoutManager(
            applicationContext,
            RecyclerView.HORIZONTAL,
            false
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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
        toolbar_title.text = "동네생활 글쓰기"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}