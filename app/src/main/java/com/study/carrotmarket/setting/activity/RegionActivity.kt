package com.study.carrotmarket.setting.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.study.carrotmarket.R
import kotlinx.android.synthetic.main.activity_region.*
import kotlinx.android.synthetic.main.layout_region.view.*
import kotlinx.android.synthetic.main.toolbar_region.*

class RegionActivity : AppCompatActivity() {
    private lateinit var regionRecyclerView:RegionRecyclerView
    private lateinit var regionList:List<RegionSettingActivity.StaticList.LocationInfo>
    private lateinit var originRegionList:List<RegionSettingActivity.StaticList.LocationInfo>

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: RegionLocationCallback
    private lateinit var currentLatLng: LatLng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_region)

        regionRecyclerView = RegionRecyclerView()
        region_recyclerview.apply {
            adapter = regionRecyclerView
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

        originRegionList = RegionSettingActivity.regionTotalList.sortedBy {
            it.distance
        }

        regionList = originRegionList

        region_layout_current_location_find.setOnClickListener {
            if (::currentLatLng.isInitialized) {
                calRegionListByDistance()
                regionList = originRegionList.sortedBy { it.distance }
                regionRecyclerView.notifyDataSetChanged()
            } else {
                Toast.makeText(this,"3D Fix 전입니다. 잠시 후 다시 시도해주세요.",Toast.LENGTH_SHORT).show()
            }
        }

        toolbar_iv_cancel.run {
            setOnClickListener {
                toolbar_edit_tv.text = null
            }
            visibility = View.GONE
        }

        region_back_btn.setOnClickListener {
            finish()
        }

        toolbar_edit_tv.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                toolbar_iv_cancel.visibility = if (count > 0) View.VISIBLE else View.GONE
                search(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        initLocation()
    }

    inner class RegionRecyclerView : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = layoutInflater.inflate(R.layout.layout_region, parent,false)
            return RegionViewHolder(view)
        }

        inner class RegionViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = (holder as RegionViewHolder).itemView
            viewHolder.tv_region.text = StringBuilder().apply {
                append(regionList[position].province)
                if (regionList[position].province != "") append(" ")
                append(regionList[position].city)
                append(" ")
                append(regionList[position].district)
                if (regionList[position].district != "") append(" ")
                append(regionList[position].neighborhood)
            }.toString()

            viewHolder.layout_region.setOnClickListener {
                val intent = Intent(baseContext, RegionSettingActivity::class.java).apply {
                    putExtra("selectList",regionList[position])
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                setResult(RESULT_OK,intent)
                finish()
            }
        }

        override fun getItemCount(): Int {
            return regionList.size
        }
    }

    private fun search(word:String) {
        regionList = originRegionList.filter {it.toString().contains(word)}
        regionRecyclerView.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        registerLocationListener()
    }
    override fun onStop() {
        super.onStop()
        unRegisterLocationListener()
    }

    private fun initLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = RegionLocationCallback()
        locationRequest = LocationRequest()
        ActivityCompat.requestPermissions(this,arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION),1000)
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
    }

    private fun registerLocationListener() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d("heo","permission not granted")
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null)
    }

    private fun unRegisterLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    inner class RegionLocationCallback : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            val currentLocation = locationResult?.lastLocation
            currentLocation?.run {
                currentLatLng = LatLng(latitude,longitude)
            }
        }
    }

    private fun calRegionListByDistance() {
        for (list in originRegionList) list.distance = betweenDistance(
            currentLatLng.latitude,
            currentLatLng.longitude,
            list.latitude,
            list.longitude
        )
    }

    private fun betweenDistance(
        latitude1: Double,
        longitude1: Double,
        latitude2: Double,
        longitude2: Double
    ): Float {
        val standard = Location("Standard").apply {
            latitude = latitude1
            longitude = longitude1
        }
        val comparison = Location("Comparison").apply {
            latitude = latitude2
            longitude = longitude2
        }
        return standard.distanceTo(comparison)
    }
}