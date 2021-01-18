package com.study.carrotmarket.presenter.setting

import android.content.Context
import android.location.Location
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.study.carrotmarket.constant.LocationInfo
import com.study.carrotmarket.constant.RegionSettingContract
import com.study.carrotmarket.model.setting.SettingModel

@RequiresApi(Build.VERSION_CODES.N)
class RegionSettingPresenter:RegionSettingContract.Presenter {
    override lateinit var view: RegionSettingContract.View

    private var regionNearByList:ArrayList<LocationInfo> = arrayListOf()

    override fun loadRegionList() {
        SettingModel.loadRegionList()
    }

    override fun setProgressCount(progress: Int) {
        SettingModel.setProgressCount(progress)
    }

    override fun getProgressCount(): Int {
        return SettingModel.getProgressCount()
    }

    override fun setSelectedNumber(num: Int) {
        SettingModel.setSelectedNumber(num)
    }

    override fun getSelectedNumber(): Int {
        return SettingModel.getSelectedNumber()
    }

    override fun calNearByRegion(position:Int) {
        val distance: Float =
            when (position) {
                0 -> 3000.0F
                1 -> 5000.0F
                2 -> 7000.0F
                3 -> 10000.0F
                else -> 0.0F
            }

        val list = SettingModel.regionBaseList.filter {
            it.distance < distance
        }.sortedBy {
            it.distance
        }
        regionNearByList.clear()
        regionNearByList.addAll(list)
        Log.d("heo",regionNearByList.toString())
        view.updateNeighborhoodCount(regionNearByList.size)
    }

    override fun setContext(context: Context) {
        SettingModel.setContext(context)
    }

    override fun sortRegionList(currentPosition: LocationInfo) {
        for (list in SettingModel.regionBaseList) list.distance = betweenDistance(
            currentPosition.latitude,
            currentPosition.longitude,
            list.latitude,
            list.longitude
        )
    }

    override fun getNearByRegionList(): ArrayList<LocationInfo> {
        return regionNearByList
    }

    override fun loadSelectedLocationList(): Pair<LocationInfo?, LocationInfo?> {
        return SettingModel.loadSelectedLocationList()
    }

    override fun saveSelectedLocationList(selectedFirstLocation: LocationInfo?, selectedSecondLocation: LocationInfo?) {
        SettingModel.saveSelectedLocationList(selectedFirstLocation, selectedSecondLocation)
        // TODO : send location info to server
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