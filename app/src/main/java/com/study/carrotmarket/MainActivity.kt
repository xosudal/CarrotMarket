package com.study.carrotmarket

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home.view.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private val mainFragment = MainFragment()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    }

    private fun registerFirebaseTokenToServer() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener( OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed ${task.exception}")
                }
                val token = task.result?.token
                Log.d(TAG, "toKen: $token")
            })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initNotification() {
        val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
        notificationManager?.createNotificationChannel(
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
