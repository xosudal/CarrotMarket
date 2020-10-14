package com.study.carrotmarket

import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

class RestApi {
    interface UsedArticleImpl {
        @Multipart
        @POST("/used/addUsedItem")
        fun setUsedArticle(
            @Part uploadImgs : List<MultipartBody.Part>
        ) : Observable<String>
    }

    companion object {
        fun setUsedArticle(content : List<MultipartBody.Part>) : Observable<String> {
            return RetrofitCreator.create(UsedArticleImpl::class.java, RetrofitCreator.CARROTMARKET_API_BASE_URL).setUsedArticle(content)
        }
    }
}