package com.study.carrotmarket.constant

interface RegionContract {
    interface View {

    }

    interface Presenter {

        fun initLocation()
        fun registerLocationListener()
        fun unRegisterLocationListener()
        fun calRegionListByDistance()
        fun createRegionList()
        fun search(word:String):List<LocationInfo>
        fun getRegionList():List<LocationInfo>
    }
}