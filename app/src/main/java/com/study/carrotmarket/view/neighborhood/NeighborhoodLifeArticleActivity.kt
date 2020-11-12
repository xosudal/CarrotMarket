package com.study.carrotmarket.view.neighborhood

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_write_neighborlife.*
import kotlinx.android.synthetic.main.toolbar.*

class NeighborhoodLifeArticleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_neighborlife)

        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        toolbar_title?.text = "동네생활 글쓰기"
        neighborlife_content_edittext.hint = getString(R.string.neighborlife_content_hint, "도화동")

        upload_pics.setOnClickListener {

        }

        location_pics.setOnClickListener {

        }

        subject_constraints.setOnClickListener {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.write_menu, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            R.id.menu_write_article_complete -> sendUsedArticle()
        }
        return super.onOptionsItemSelected(item);
    }

    private fun sendUsedArticle() {
    }
}
