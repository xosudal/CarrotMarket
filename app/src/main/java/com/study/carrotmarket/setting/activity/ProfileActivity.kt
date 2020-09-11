package com.study.carrotmarket.setting.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        settingToolbar()

        profile_layout_count_sales.setOnClickListener {
            Log.d("heo","click layout!")
        }

        rootView.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val params = (profile_layout_first_temp.layoutParams as ViewGroup.MarginLayoutParams)
                params.setMargins(0,0,(profile_layout_first_temp.width)/2,0)
                profile_layout_first_temp.layoutParams = params
                rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)}
        })

        profile_progressbar.progress = 36

        profile_layout_manner.setOnClickListener {
            val popupView = layoutInflater.inflate(R.layout.layout_text_ballon, profile_layout_manner, false)
            val popupWindow = PopupWindow(popupView,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, true).apply {
                isFocusable = true
            }
            popupWindow.showAsDropDown(profile_layout_manner)
            CoroutineScope(Dispatchers.Default).launch {
                delay(3000)
                CoroutineScope(Dispatchers.Main).launch {
                    if (popupWindow.isShowing)  popupWindow.dismiss()
                }
            }
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
        toolbar_title.text = "프로필"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}