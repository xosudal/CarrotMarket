package com.study.carrotmarket.view.setting.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.UserInfo
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage
    lateinit var firestore: FirebaseFirestore
    var profileImageUri:Any? = null
    private val PROFILE_EDIT = 1000
    var userInfo: UserInfo? = UserInfo()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        settingToolbar()
        auth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        firestore = FirebaseFirestore.getInstance()

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

    override fun onResume() {
        super.onResume()
        userInfo = Gson().fromJson(this.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE).getString("USER_INFO",null),
            UserInfo::class.java)
        getProfile()
    }

    private fun getProfile() {
        userInfo?.let {
            profile_user_name.text = "${it.userId}"
            profile_user_name_hash.text = "${it.uid}"
            Glide.with(this).load(it.imageUri).diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop().override(profile_user_image.width,profile_user_image.height).into(profile_user_image)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PROFILE_EDIT && resultCode == RESULT_OK) {
            userInfo = data?.getParcelableExtra("USER_INFO")
            this.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE).edit().putString("USER_INFO",Gson().toJson(userInfo)).apply()
            Log.d("heo","Draw in ac result")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile_setting -> {
                val intent = Intent(this,ProfileEditActivity::class.java).apply {
                    putExtra("USER_INFO",userInfo)
                }
                startActivityForResult(intent,PROFILE_EDIT)
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