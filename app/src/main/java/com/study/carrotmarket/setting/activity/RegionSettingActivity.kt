package com.study.carrotmarket.setting.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_region_setting.*
import kotlinx.android.synthetic.main.toolbar.*

class RegionSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region_setting)
        settingToolbar()


        region_seek_bar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                setImageAlpha(p1)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

    override fun onResume() {
        super.onResume()
        region_seek_bar.progress = 0
        setImageAlpha(0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
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
        toolbar_title.text = "내 동네 설정"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setImageAlpha(position:Int) {
        when(position) {
            0 -> {
                iv_region_2.alpha = 0.3F
                iv_region_3.alpha = 0.3F
                iv_region_4.alpha = 0.3F
            }
            1 -> {
                iv_region_2.alpha = 0.8F
                iv_region_3.alpha = 0.3F
                iv_region_4.alpha = 0.3F
            }
            2 -> {
                iv_region_2.alpha = 0.8F
                iv_region_3.alpha = 0.6F
                iv_region_4.alpha = 0.3F
            }
            3 -> {
                iv_region_2.alpha = 0.8F
                iv_region_3.alpha = 0.6F
                iv_region_4.alpha = 0.45F
            }
        }
    }
}