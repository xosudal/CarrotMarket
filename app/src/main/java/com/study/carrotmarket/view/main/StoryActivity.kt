package com.study.carrotmarket.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.study.carrotmarket.R
import com.study.carrotmarket.view.main.adapter.StoryRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_story.story_recyclerview
import kotlinx.android.synthetic.main.toolbar.*

class StoryActivity : AppCompatActivity() {
    private lateinit var storyRecyclerView:StoryRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
        settingToolbar()
        storyRecyclerView = StoryRecyclerViewAdapter()
        story_recyclerview.apply {
            adapter = storyRecyclerView
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_story, menu)
        return true
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}