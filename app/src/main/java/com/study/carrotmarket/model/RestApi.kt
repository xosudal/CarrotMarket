package com.study.carrotmarket.model

import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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
            return RetrofitCreator.create(
                UsedArticleImpl::class.java,
                RetrofitCreator.CARROTMARKET_API_BASE_URL
            ).setUsedArticle(content)
        }
    }
}