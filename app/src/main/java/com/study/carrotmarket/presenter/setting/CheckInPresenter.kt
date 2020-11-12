package com.study.carrotmarket.presenter.setting

import com.study.carrotmarket.constant.CheckInContract
import com.study.carrotmarket.model.setting.CheckInModel

class CheckInPresenter() : CheckInContract.Presenter {
    override lateinit var view: CheckInContract.View

    override lateinit var model: CheckInModel

    override fun getCurrentLocation() {
        model.getCurrentLocation()
    }

    override fun initLocation() {
        model.initLocation()
    }

    override fun registerLocationListener() {
        model.registerLocationListener()
    }

    override fun unRegisterLocationListener() {
        model.unRegisterLocationListener()
    }
}