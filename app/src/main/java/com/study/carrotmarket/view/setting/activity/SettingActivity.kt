package com.study.carrotmarket.view.setting.activity

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.TabHost
import android.widget.TabHost.TabContentFactory
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.SettingContract
import com.study.carrotmarket.constant.TimePreference
import com.study.carrotmarket.presenter.setting.SettingPresenter
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.dialog_disturb_time_setting.*
import kotlinx.android.synthetic.main.toolbar.*


class SettingActivity : AppCompatActivity(), SettingContract.View {
    private lateinit var presenter:SettingPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        presenter = SettingPresenter().apply {
            view = this@SettingActivity
        }
        settingToolbar()

        setting_switch_set_major_alarm.setOnCheckedChangeListener{ _, isChecked ->
            presenter.settingPreference.majorAlarmIsChecked = isChecked
        }

        setting_switch_set_minor_alarm.setOnCheckedChangeListener{ _, isChecked ->
            presenter.settingPreference.minorAlarmIsChecked = isChecked
        }

        setting_switch_set_disturb.setOnCheckedChangeListener { _, isChecked ->
            setting_disturb_time_setting_layout.visibility = if (isChecked) View.VISIBLE else View.GONE
            presenter.settingPreference.disturbIsChecked = isChecked
        }

        setting_switch_set_vibrate.setOnCheckedChangeListener{ _, isChecked ->
            presenter.settingPreference.vibrateIsChecked = isChecked
        }

        setting_disturb_time_setting_layout.setOnClickListener {
            showTimePickerDialog()
        }

        setting_alarm_sound.setOnClickListener {
            showDialogForAlarmSetting(presenter.getAlarmSoundList())
        }

        setting_language.setOnClickListener {
            showDialogForLanguageSetting(presenter.getLanguageList())
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
        presenter.setContext(this)
        presenter.loadPreference()
        setPreference()
        setTextDisturbTime(presenter.timePreference)
    }

    override fun onStop() {
        super.onStop()
        presenter.savePreference()
    }

    private fun setPreference() {
        setting_switch_set_major_alarm.isChecked = presenter.settingPreference.majorAlarmIsChecked
        setting_switch_set_minor_alarm.isChecked = presenter.settingPreference.minorAlarmIsChecked
        setting_switch_set_disturb.isChecked = presenter.settingPreference.disturbIsChecked
        setting_switch_set_vibrate.isChecked = presenter.settingPreference.vibrateIsChecked
        setting_disturb_time_setting_layout.visibility = if (presenter.settingPreference.disturbIsChecked) View.VISIBLE else View.GONE
        setting_alarm_sound_tv.text = presenter.getAlarmSoundList()[presenter.settingPreference.alarmSoundIndex]
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

    private fun showDialogForAlarmSetting(list: Array<String>) {
        var index = presenter.settingPreference.alarmSoundIndex
        AlertDialog.Builder(this).apply {
            setSingleChoiceItems(list, index) { _, which ->
                index = which
            }
            setPositiveButton("확인") { _, _ ->
                presenter.settingPreference.alarmSoundIndex = index
                setting_alarm_sound_tv.text = list[presenter.settingPreference.alarmSoundIndex]
            }
            setNegativeButton("취소", null)
        }.create().show()
    }

    private fun showDialogForLanguageSetting(list: Array<String>) {
        AlertDialog.Builder(this).apply {
            setSingleChoiceItems(list, presenter.settingPreference.language) { dialog, which ->
                presenter.settingPreference.language = which
                dialog.dismiss()
            }
            setTitle("언어")
        }.create().show()
    }

    private fun showDialogForCache() {
        AlertDialog.Builder(this).apply {
            setMessage(R.string.dialog_text_cache)
            setPositiveButton("확인") { _, _ ->
                presenter.clearCacheByUser()
            }
            setNegativeButton("취소", null)
        }.create().show()
    }

    private fun showDialogForLogout() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.logout)
            setMessage(R.string.dialog_text_logout_content)
            setPositiveButton("확인") { _, _ ->
                Toast.makeText(context,"로그아웃!",Toast.LENGTH_SHORT).show()
                presenter.logOut()
            }
            setNegativeButton("취소", null)
        }.create().show()
    }

    private fun showTimePickerDialog() {
        val hourSetting = TimePreference()
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
            hourSetting.fromHour = hour
            hourSetting.fromMinute = minute
        }

        timePickerTo.setOnTimeChangedListener { _, hour, minute ->
            hourSetting.toHour = hour
            hourSetting.toMinute = minute
        }

        dlg.dialog_time_setting_yes.setOnClickListener {
            presenter.timePreference = hourSetting
            setTextDisturbTime(hourSetting)
            dlg.dismiss()
        }

        dlg.dialog_time_setting_no.setOnClickListener {
            dlg.dismiss()
        }
    }

    private fun setTextDisturbTime(dntTime:TimePreference) {
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

    override fun showDeleteSuccess() {
        Toast.makeText(this,"삭제되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun showDeleteFail() {
        Toast.makeText(this,"삭제를 실패했습니다.", Toast.LENGTH_SHORT).show()
    }
}