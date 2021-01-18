package com.study.carrotmarket.model

import com.study.carrotmarket.constant.CarrotMarketRepository
import com.study.carrotmarket.constant.DetailUsedItemResponse
import com.study.carrotmarket.constant.ResponseUpdateUser
import com.study.carrotmarket.constant.SimpleUsedItemResponse
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

object CarrotMarketDataRepository : CarrotMarketRepository {
    override fun sendUsedArticle(data : List<MultipartBody.Part> ): Single<String> {
        return RestApi.setUsedArticle(data)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getDetailUsedItem(id : Int, e_mail : String): Observable<DetailUsedItemResponse> {
        return RestApi.getDetailUsedItem(id, e_mail)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSimpleUsedItem() : Observable<List<SimpleUsedItemResponse>> {
        return RestApi.getSimpleUsedItem()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun sendUserInfo(data : List<MultipartBody.Part> ): Single<List<ResponseUpdateUser>> {
        return RestApi.updateUser(data)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
