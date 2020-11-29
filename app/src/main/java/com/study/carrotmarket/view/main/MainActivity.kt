package com.study.carrotmarket.view.main

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.UserInfo
import com.study.carrotmarket.view.chat.ChattingFragment
import com.study.carrotmarket.view.chat.WriteBottomSheetDialogFragment
import com.study.carrotmarket.view.neighborhood.NeighborhoodFragment
import com.study.carrotmarket.view.setting.fragment.MyCarrotFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home.view.*
import kotlinx.android.synthetic.main.layout_login_dialog.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val mainFragment = MainFragment()
    lateinit var dlg:AlertDialog
    lateinit var auth:FirebaseAuth
    lateinit var loadingDialog: LoadingDialog

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        loadingDialog = LoadingDialog(this)
        setSupportActionBar(toolbar).apply {
            title = null
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,mainFragment).commit()

        navigation_bottom.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_home -> mainFragment
                R.id.navigation_neighborhoohood -> NeighborhoodFragment()
                R.id.navigation_write -> WriteBottomSheetDialogFragment()
                R.id.navigation_chatting -> ChattingFragment()
                R.id.navigation_mycarrot -> MyCarrotFragment()
                else -> FragmentHome("Default")
            }.let { fragment ->
                if(fragment is WriteBottomSheetDialogFragment) { // Exceptionally to prevent the replace code, compare with BottomSheetDialog class type
                    fragment.show(supportFragmentManager, "show")
                }
                else {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment).commit()
                }
            }
            true
        }

        /* Init Notification */
        initNotification()
        registerFirebaseTokenToServer()

        showLoginDialog()
    }

    private fun registerFirebaseTokenToServer() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener( OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed ${task.exception}")
                    return@OnCompleteListener
                }
                val token = task.result?.token
                Log.d(TAG, "toKen: $token")
            })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initNotification() {
        val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(
            NotificationChannel(
                "0", "CH0", NotificationManager.IMPORTANCE_LOW
            )
        )
    }

    override fun onBackPressed() {
        if (navigation_bottom.selectedItemId != R.id.navigation_home) {
                navigation_bottom.selectedItemId = R.id.navigation_home
        } else {
            finishAffinity()
        }
    }

    private fun showLoginDialog() {
        if (FirebaseAuth.getInstance().currentUser != null) return

        val builder = AlertDialog.Builder(this).apply {
            setView(R.layout.layout_login_dialog)
        }
        dlg = builder.create()
        dlg.show()

        dlg.login_dialog_btn_test_account.setOnClickListener {
            dlg.dismiss()
            loginTestAccount()
        }
        dlg.login_dialog_btn_login.setOnClickListener {
            dlg.dismiss()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun loginTestAccount() {
        loadingDialog.show()
        auth.signInWithEmailAndPassword("helloworld@naver.com", "1123456")
            .addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("heo","Login Success")
                loadTestAccountInformation()
            } else {
                Log.d("heo","Login Fail")
                loadingDialog.dismiss()
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadTestAccountInformation() {
        FirebaseFirestore.getInstance().collection("ProfileImage").document(auth.currentUser?.uid.toString()).get().addOnSuccessListener {
            if (it == null) {
                loadingDialog.dismiss()
                return@addOnSuccessListener
            }
            if (it.data != null) {
                val userInfo = UserInfo(it.data!!["imageUri"].toString(),it.data!!["nickName"].toString(),it.data!!["uid"].toString(),it.data!!["userId"].toString())
                getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)?.edit()?.putString("USER_INFO", Gson().toJson(userInfo))?.apply()
                loadingDialog.dismiss()
            }
        }
    }
}

data class FragmentHome(private val fragmentName : String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.test.text = fragmentName
        super.onViewCreated(view, savedInstanceState)
    }

}
