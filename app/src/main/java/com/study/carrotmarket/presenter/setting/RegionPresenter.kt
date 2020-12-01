package com.study.carrotmarket.presenter.setting

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.study.carrotmarket.constant.LocationInfo
import com.study.carrotmarket.constant.RegionContract
import com.study.carrotmarket.model.setting.RegionListModel

@RequiresApi(Build.VERSION_CODES.N)
class RegionPresenter(private val context: Context) : RegionContract.Presenter {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: CheckInLocationCallback
    private lateinit var currentLatLng: LatLng

    init {
        RegionListModel.setContext(context)
        RegionListModel.loadRegionList()
    }

    override fun calRegionListByDistance() {
        if (!::currentLatLng.isInitialized) {
            return
        }
        for (list in RegionListModel.regionList) list.distance = betweenDistance(
            currentLatLng.latitude,
            currentLatLng.longitude,
            list.latitude,
            list.longitude
        )

        RegionListModel.regionList = RegionListModel.regionList.sortedBy { it.distance }
    }

    override fun createRegionList() {
        RegionListModel.createRegionList()
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

    override fun search(word: String):List<LocationInfo> {
        Log.d("heo","test... search!!!!")
        return RegionListModel.regionList.filter {it.toString().contains(word)}
    }

    override fun registerLocationListener() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null)
    }

    override fun unRegisterLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun initLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationCallback = CheckInLocationCallback()
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
    }

    inner class CheckInLocationCallback : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            val currentLocation = locationResult?.lastLocation
            currentLocation?.run {
                currentLatLng = LatLng(latitude,longitude)
            }
        }
    }
}