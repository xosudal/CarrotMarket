package com.study.carrotmarket.constant

import com.google.android.gms.maps.model.LatLng
import com.study.carrotmarket.model.setting.CheckInModel

interface CheckInContract {
    interface View {
        fun updateCurrentLocation(latLng : LatLng, currentAddress : String)
        fun showCurrentLocation(latLng : LatLng)
    }

    interface Presenter {
        var view:View
        var model: CheckInModel

        fun getCurrentLocation()
        fun initLocation()
        fun registerLocationListener()
        fun unRegisterLocationListener()
    }
}