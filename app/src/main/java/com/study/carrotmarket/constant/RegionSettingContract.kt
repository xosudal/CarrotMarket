package com.study.carrotmarket.constant

import android.content.Context

interface RegionSettingContract {
    interface View {
        fun updateNeighborhoodCount(count:Int)
    }

    interface Presenter {
        var view:View

        fun loadRegionList()
        fun setProgressCount(progress:Int)
        fun getProgressCount():Int
        fun setSelectedNumber(num:Int)
        fun getSelectedNumber():Int
        fun calNearByRegion(position:Int)
        fun setContext(context: Context)
        fun sortRegionList(currentPosition: LocationInfo)
        fun getNearByRegionList():ArrayList<LocationInfo>
        fun loadSelectedLocationList():Pair<LocationInfo?, LocationInfo?>
        fun saveSelectedLocationList(selectedFirstLocation:LocationInfo?, selectedSecondLocation:LocationInfo?)
    }
}