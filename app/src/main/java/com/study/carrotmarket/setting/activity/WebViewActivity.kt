package com.study.carrotmarket.setting.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.study.carrotmarket.LoginActivity
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.toolbar.*

class WebViewActivity : AppCompatActivity() {
    var mode = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        settingToolbar()

        if (mode == 0) startActivity(Intent(this,LoginActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_web_view,menu)
        menu?.findItem(R.id.web_view_share)?.isVisible = when(mode) {
            0,1 -> false
            2 -> true
            else -> false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.web_view_share -> {
                startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply{
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT,"공유할 text")
                },"당근마켓 공유하기"))
                return true
            }
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return true
    }

    private fun settingToolbar() {
        setSupportActionBar(toolbar).apply {
            title = null
        }
        mode = intent.getIntExtra("mode", 0)
        toolbar_title.text = when (mode) {
            0 -> "초대하기"
            1 -> "공지사항"
            2 -> "고객센터"
            else -> "Error"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}