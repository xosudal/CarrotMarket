package com.study.carrotmarket.presenter.setting

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.study.carrotmarket.constant.CheckInContract
import com.study.carrotmarket.view.setting.activity.CheckInActivity

class CheckInPresenter(private var context: CheckInActivity) : CheckInContract.Presenter {
    override lateinit var view: CheckInContract.View

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: CheckInLocationCallback
    private lateinit var currentLatLng: LatLng

    var list = mutableListOf<Address>()
    private var geoCoder: Geocoder = Geocoder(context)

    override fun getCurrentLocation() {
        view.showCurrentLocation(currentLatLng)
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
                list = geoCoder.getFromLocation(latitude,longitude,1)
                view.updateCurrentLocation(currentLatLng,list[0].thoroughfare)
            }
        }
    }
}