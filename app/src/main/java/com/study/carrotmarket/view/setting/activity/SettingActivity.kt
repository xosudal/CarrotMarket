package com.study.carrotmarket.view.setting.activity

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.TabHost
import android.widget.TabHost.TabContentFactory
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.dialog_disturb_time_setting.*
import kotlinx.android.synthetic.main.toolbar.*


class SettingActivity : AppCompatActivity() {
    private var data = SettingPreference()
    private var time = DoNotDisturbTime()
    private val alarmSoundList =
        arrayOf(
            "당근(귀요미 챙)",
            "땅근(다인이), 당근이 아떠요~(다은이)",
            "당근이 왔어요(BB)",
            "당근 주세요(준환이)",
            "애미야 당근 왔다(민주)",
            "기본 알림음"
        )
    private val languageList =
        arrayOf(
            "English",
            "한국어"
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

        setting_language.setOnClickListener {
            showDialogForLanguageSetting(languageList)
        }

        setting_clear_app_cache.setOnClickListener {
            showDialogForCache()
        }

        setting_log_out.setOnClickListener {
            showDialogForLogout()
        }
    }

    override fun onResume() {
        super.onResume()
        setting_switch_set_major_alarm.isChecked = data.majorAlarmIsChecked
        setting_switch_set_minor_alarm.isChecked = data.minorAlarmIsChecked
        setting_switch_set_disturb.isChecked = data.disturbIsChecked
        setting_switch_set_vibrate.isChecked = data.vibrateIsChecked
        setting_disturb_time_setting_layout.visibility = if (data.disturbIsChecked) View.VISIBLE else View.GONE
        setting_alarm_sound_tv.text = alarmSoundList[data.alarmSoundIndex]
        setTextDistrubTime(time)
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
        data.majorAlarmIsChecked = this.getPreferences(0).getBoolean("MAJOR_ALARM", false)
        data.minorAlarmIsChecked = this.getPreferences(0).getBoolean("MINOR_ALARM", false)
        data.disturbIsChecked = this.getPreferences(0).getBoolean("DISTURB", false)
        data.vibrateIsChecked = this.getPreferences(0).getBoolean("VIBRATE", false)
        data.alarmSoundIndex = this.getPreferences(0).getInt("ALARM", 0)
        data.language = this.getPreferences(0).getInt("LANGUAGUE",1)
        time.fromHour = this.getPreferences(0).getInt("FROM_HOUR",23)
        time.fromMinute = this.getPreferences(0).getInt("FROM_MINUTE",0)
        time.toHour = this.getPreferences(0).getInt("TO_HOUR",8)
        time.toMinute = this.getPreferences(0).getInt("TO_MINUTE",0)
    }

    private fun showDialogForAlarmSetting(list: Array<String>) {
        var index = data.alarmSoundIndex
        AlertDialog.Builder(this).apply {
            setSingleChoiceItems(list, index) { _, which ->
                index = which
            }
            setPositiveButton("확인") { _, _ ->
                data.alarmSoundIndex = index
                getPreferences(0).edit().putInt("ALARM", index).apply()
                setting_alarm_sound_tv.text = alarmSoundList[data.alarmSoundIndex]
            }
            setNegativeButton("취소", null)
        }.create().show()
    }

    private fun showDialogForLanguageSetting(list: Array<String>) {
        AlertDialog.Builder(this).apply {
            setSingleChoiceItems(list, data.language) { dialog, which ->
                data.language = which
                getPreferences(0).edit().putInt("LANGUAGE", data.language).apply()
                dialog.dismiss()
            }
            setTitle("언어")
        }.create().show()
    }

    private fun showDialogForCache() {
        AlertDialog.Builder(this).apply {
            setMessage(R.string.dialog_text_cache)
            setPositiveButton("확인") { _, _ ->
                clearCacheByUser()
            }
            setNegativeButton("취소", null)
        }.create().show()
    }

    private fun clearCacheByUser() {
        if (cacheDir.delete()) {
            Toast.makeText(this,"삭제되었습니다.",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"삭제를 실패했습니다.",Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDialogForLogout() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.logout)
            setMessage(R.string.dialog_text_logout_content)
            setPositiveButton("확인") { _, _ ->
                Toast.makeText(context,"로그아웃!",Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut()
            }
            setNegativeButton("취소", null)
        }.create().show()
    }

    private fun showTimePickerDialog() {
        val hourSetting = time
        val dlg = Dialog(this)
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_disturb_time_setting)
        dlg.setCancelable(true)
        val timePickerFrom = TimePicker(baseContext).apply {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                hour = hourSetting.fromHour
                minute = hourSetting.fromMinute
            } else {
                currentHour = hourSetting.fromHour
                currentMinute = hourSetting.fromMinute
            }
            setIs24HourView(false)
        }
        val timePickerTo = TimePicker(baseContext).apply {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                hour = hourSetting.toHour
                minute = hourSetting.toMinute
            } else {
                currentHour = hourSetting.toHour
                currentMinute = hourSetting.toMinute
            }
            setIs24HourView(false)
        }
        val tabhost: TabHost = dlg.findViewById(R.id.disturb_tab_host)
        tabhost.setup()
        val ts1 = tabhost.newTabSpec("From").apply {
            setContent(TabContentFactory { timePickerFrom } )
            setIndicator("FROM")
        }
        tabhost.addTab(ts1)

        val ts2 = tabhost.newTabSpec("To").apply {
            setContent(TabContentFactory { timePickerTo })
            setIndicator("TO")
        }
        tabhost.addTab(ts2)

        dlg.show()

        timePickerFrom.setOnTimeChangedListener { _, hour, minute ->
            Log.d("heo","$hour, $minute")
            hourSetting.fromHour = hour
            hourSetting.fromMinute = minute
        }

        timePickerTo.setOnTimeChangedListener { _, hour, minute ->
            Log.d("heo","$hour, $minute")
            hourSetting.toHour = hour
            hourSetting.toMinute = minute
        }

        dlg.dialog_time_setting_yes.setOnClickListener {
            setPreferenceDistrubTime(hourSetting)
            setTextDistrubTime(hourSetting)
            dlg.dismiss()
        }

        dlg.dialog_time_setting_no.setOnClickListener {
            dlg.dismiss()
        }
    }



    private fun setPreferenceDistrubTime (dntTime:DoNotDisturbTime) {
        time = dntTime
        this.getPreferences(0).edit().apply {
            putInt("FROM_HOUR", time.fromHour)
            putInt("FROM_MINUTE", time.fromMinute)
            putInt("TO_HOUR", time.toHour)
            putInt("TO_MINUTE", time.toMinute)
        }.apply()
    }

    private fun setTextDistrubTime(dntTime:DoNotDisturbTime) {
        val from24Hour = if (dntTime.fromHour in 0..11) "오전" else "오후"
        val to24Hour = if (dntTime.toHour in 0..11) "오전" else "오후"

        val fromHourView = if (dntTime.fromHour > 12) dntTime.fromHour-12 else dntTime.fromHour
        val toHourView = if (dntTime.toHour > 12) dntTime.toHour-12 else dntTime.toHour

        setting_do_not_disturb_tv.text = getString(R.string.setting_do_not_disturb_time,
            String.format("%02d",fromHourView),
            String.format("%02d",dntTime.fromMinute),
            from24Hour,
            String.format("%02d",toHourView),
            String.format("%02d",dntTime.toMinute),
            to24Hour)
    }
}



data class SettingPreference(
    var majorAlarmIsChecked: Boolean = false,
    var minorAlarmIsChecked: Boolean = false,
    var vibrateIsChecked: Boolean = false,
    var disturbIsChecked: Boolean = false,
    var alarmSoundIndex: Int = 0,
    var language: Int = 1
)
data class DoNotDisturbTime (
    var fromHour: Int = 23,
    var fromMinute: Int = 0,
    var toHour: Int = 8,
    var toMinute : Int = 0
)