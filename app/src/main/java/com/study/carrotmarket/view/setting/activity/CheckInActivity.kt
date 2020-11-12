package com.study.carrotmarket.view.setting.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.study.carrotmarket.R
import com.study.carrotmarket.constant.CheckInContract
import com.study.carrotmarket.model.setting.CheckInModel
import com.study.carrotmarket.presenter.setting.CheckInPresenter
import kotlinx.android.synthetic.main.activity_check_in.*
import kotlinx.android.synthetic.main.toolbar.*

class CheckInActivity : AppCompatActivity(), OnMapReadyCallback, CheckInContract.View {
    private lateinit var mMap: GoogleMap
    private val TAG = "CheckInActivity"
    private var mapMarker : Marker? = null

    private lateinit var presenter: CheckInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_check_in)
        settingToolbar()

        presenter = CheckInPresenter().apply {
            view = this@CheckInActivity
            model = CheckInModel(this@CheckInActivity)
        }

        presenter.initLocation()

        check_in_map_btn.setOnClickListener {
            presenter.getCurrentLocation()
        }

        check_in_faq_layout.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java).apply {
                putExtra("mode",2)
            })
        }

        check_in_auth_layout.setOnClickListener {
            Toast.makeText(this,"동네 인증 성공!",Toast.LENGTH_SHORT).show()
            finish()
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.check_in_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onResume() {
        super.onResume()
        presenter.registerLocationListener()
    }

    override fun onPause() {
        super.onPause()
        presenter.unRegisterLocationListener()
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
        toolbar_title.text = "동네 인증하기"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun updateCurrentLocation(latLng: LatLng, currentAddress:String) {
        mapMarker?.remove()
        mapMarker = mMap.addMarker(MarkerOptions().position(latLng).title("currentPosition"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
        setCurrentPositionText(currentAddress)
    }

    override fun showCurrentLocation(latLng: LatLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
        Toast.makeText(this,"현재 위치를 찾고 있어요", Toast.LENGTH_SHORT).show()
    }

    private fun setCurrentPositionText(position:String) {
        val totalString = getString(R.string.check_in_current_position,position)
        setSpanText(totalString, position).let {
            check_in_current_position_tv.text = it
        }
    }

    private fun setSpanText(totalString:String, current: String) : SpannableString {
        val span = SpannableString(totalString)
        val start = totalString.indexOf(current) - 1
        val end = start + current.length + 1
        span.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
        return span
    }
}