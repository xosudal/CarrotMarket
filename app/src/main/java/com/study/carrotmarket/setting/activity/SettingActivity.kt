package com.study.carrotmarket.setting.activity

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.TabHost
import android.widget.TabHost.TabContentFactory
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.toolbar.*


class SettingActivity : AppCompatActivity() {
    private var majorAlarmIsChecked: Boolean = false
    private var minorAlarmIsChecked: Boolean = false
    private var vibrateIsChecked: Boolean = false
    private var disturbIsChecked: Boolean = false
    private var alarmSoundIndex: Int = 0

    private val alarmSoundList =
        arrayOf(
            "당근(귀요미 챙)",
            "땅근(다인이), 당근이 아떠요~(다은이)",
            "당근이 왔어요(BB)",
            "당근 주세요(준환이)",
            "애미야 당근 왔다(민주)",
            "기본 알림음"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        settingToolbar()
        loadPreference()




        setting_switch_set_major_alarm.setOnCheckedChangeListener{ _, isChecked ->
            this.getPreferences(0).edit().apply{
                putBoolean("MAJOR_ALARM", isChecked)
            }.apply()
        }

        setting_switch_set_minor_alarm.setOnCheckedChangeListener{ _, isChecked ->
            this.getPreferences(0).edit().apply{
                putBoolean("MINOR_ALARM", isChecked)
            }.apply()
        }

        setting_switch_set_disturb.setOnCheckedChangeListener { _, isChecked ->
            setting_disturb_time_setting_layout.visibility = if (isChecked) View.VISIBLE else View.GONE
            this.getPreferences(0).edit().apply{
                putBoolean("DISTURB", isChecked)
            }.apply()
        }

        setting_switch_set_vibrate.setOnCheckedChangeListener{ _, isChecked ->
            this.getPreferences(0).edit().apply{
                putBoolean("VIBRATE", isChecked)
            }.apply()
        }

        setting_disturb_time_setting_layout.setOnClickListener {
            showTimePickerDialog()
        }

        setting_alarm_sound.setOnClickListener {
            showDialogForAlarmSetting(alarmSoundList)
        }
    }

    override fun onResume() {
        super.onResume()
        setting_switch_set_major_alarm.isChecked = majorAlarmIsChecked
        setting_switch_set_minor_alarm.isChecked = minorAlarmIsChecked
        setting_switch_set_disturb.isChecked = disturbIsChecked
        setting_switch_set_vibrate.isChecked = vibrateIsChecked
        setting_disturb_time_setting_layout.visibility = if (disturbIsChecked) View.VISIBLE else View.GONE
        setting_alarm_sound_tv.text = alarmSoundList[alarmSoundIndex]
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
        toolbar_title.text = "설정"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadPreference() {
        majorAlarmIsChecked = this.getPreferences(0).getBoolean("MAJOR_ALARM", false)
        minorAlarmIsChecked = this.getPreferences(0).getBoolean("MINOR_ALARM", false)
        disturbIsChecked = this.getPreferences(0).getBoolean("DISTURB", false)
        vibrateIsChecked = this.getPreferences(0).getBoolean("VIBRATE", false)
        alarmSoundIndex = this.getPreferences(0).getInt("ALARM", 0)
    }

    private fun showDialogForAlarmSetting(list: Array<String>) {
        var index = alarmSoundIndex
        AlertDialog.Builder(this).apply {
            setSingleChoiceItems(list, index) { _, which ->
                index = which
            }
            setPositiveButton("확인") { _, _ ->
                alarmSoundIndex = index
                getPreferences(0).edit().putInt("ALARM", index).apply()
                setting_alarm_sound_tv.text = alarmSoundList[alarmSoundIndex]
            }
            setNegativeButton("취소", null)
        }.create().show()
    }

    private fun showTimePickerDialog() {
        val dlg = Dialog(this)
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_disturb_time_setting)
        dlg.setCancelable(false)
        val timePickerFrom = TimePicker(baseContext)
        val timePickerTo = TimePicker(baseContext)
        val tabhost: TabHost = dlg.findViewById(R.id.disturb_tab_host)
        tabhost.setup()
        val ts1 = tabhost.newTabSpec("Tab Spec 1").apply {
            setContent(TabContentFactory { timePickerFrom } )
            setIndicator("FROM")
        }
        tabhost.addTab(ts1)

        val ts2 = tabhost.newTabSpec("Tab Spec 2").apply {
            setContent(TabContentFactory { timePickerTo })
            setIndicator("TO")
        }
        tabhost.addTab(ts2)

        dlg.show()

        timePickerFrom.setOnTimeChangedListener { timePicker, hour, minute ->
            Log.d("heo","$hour, $minute")
        }

        timePickerTo.setOnTimeChangedListener { timePicker, hour, minute ->
            Log.d("heo","$hour, $minute")
        }
    }
}