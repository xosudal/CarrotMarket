package com.study.carrotmarket.model.setting

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.study.carrotmarket.presenter.setting.CheckInPresenter
import com.study.carrotmarket.view.setting.activity.CheckInActivity

class CheckInModel(private var context: CheckInActivity) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: CheckInLocationCallback
    private lateinit var currentLatLng: LatLng
    var list = mutableListOf<Address>()
    private var geoCoder: Geocoder = Geocoder(context)
    private var presenter = CheckInPresenter()

    fun initLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        locationCallback = CheckInLocationCallback()
        locationRequest = LocationRequest()
        ActivityCompat.requestPermissions(
            context,arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION),1000)
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
    }

    fun registerLocationListener() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null)
    }

    fun unRegisterLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    inner class CheckInLocationCallback : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            val currentLocation = locationResult?.lastLocation
            currentLocation?.run {
                currentLatLng = LatLng(latitude,longitude)
                list = geoCoder.getFromLocation(latitude,longitude,1)
                presenter.view.updateCurrentLocation(currentLatLng,list[0].thoroughfare)
            }
        }
    }

    fun getCurrentLocation() {
        presenter.view.showCurrentLocation(currentLatLng)
    }
}