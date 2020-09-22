package com.study.carrotmarket.setting.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_check_in.*
import kotlinx.android.synthetic.main.toolbar.*

class CheckInActivity : AppCompatActivity(), OnMapReadyCallback{
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: CheckInLocationCallback
    private lateinit var currentLatLng: LatLng
    private val TAG = "CheckInActivity"
    private lateinit var geoCoder: Geocoder
    var list = mutableListOf<Address>()
    private var mapMarker : Marker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_check_in)
        settingToolbar()

        geoCoder = Geocoder(this)
        check_in_map_btn.setOnClickListener {
            findCurrentLocation()
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
        initLocation()
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
        registerLocationListener()
    }

    override fun onPause() {
        super.onPause()
        unRegisterLocationListener()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = CheckInLocationCallback()
        locationRequest = LocationRequest()
        ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION),1000)
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
    }

    private fun registerLocationListener() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG,"[registerLocationListener] no permission..")
            return
        }
        val result = fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null)
        Log.d(TAG,"[registerLocationListener] result : $result")
    }

    private fun unRegisterLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    inner class CheckInLocationCallback : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            val currentLocation = locationResult?.lastLocation
            currentLocation?.run {
                currentLatLng = LatLng(latitude,longitude)
                mapMarker?.remove()
                mapMarker = mMap.addMarker(MarkerOptions().position(currentLatLng).title("currentPosition"))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 17f))
                list = geoCoder.getFromLocation(latitude,longitude,1)
                setCurrentPositionText(list[0].thoroughfare)
            }
        }
    }

    private fun findCurrentLocation() {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 17f))
        Toast.makeText(this,"현재 위치를 찾고 있어요",Toast.LENGTH_SHORT).show()
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