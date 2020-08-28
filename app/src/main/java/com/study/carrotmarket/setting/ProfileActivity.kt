package com.study.carrotmarket.setting

import android.os.Bundle
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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting_profile, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile_setting -> {
                Toast.makeText(this,"setting!!!!",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.profile_setting_share -> {
                Toast.makeText(this,"share!!!!",Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}