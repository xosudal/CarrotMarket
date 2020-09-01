package com.study.carrotmarket.setting.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profile_btn_back?.setOnClickListener {
            finish()
        }
        setSupportActionBar(profile_toolbar).apply {
            title = null
        }

        profile_layout_count_sales?.setOnClickListener {
            Log.d("heo","click layout!")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting_profile, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile_setting -> {
                Toast.makeText(this, "setting!!!!", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.profile_setting_share -> {
                startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply{
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT,"공유할 text")},"당근마켓 공유하기"))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}