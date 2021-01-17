package com.study.carrotmarket.constant

import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody

interface CarrotMarketRepository {
    fun sendUsedArticle(data : List<MultipartBody.Part> ) : Single<String>
    fun getSimpleUsedItem() : Observable<List<SimpleUsedItemResponse>>
    fun getDetailUsedItem(id : Int, e_mail : String) : Observable<DetailUsedItemResponse>
    fun sendUserInfo(data : List<MultipartBody.Part> ) : Single<String>
}