package com.study.carrotmarket.model

import com.study.carrotmarket.constant.DetailUsedItemResponse
import com.study.carrotmarket.constant.SimpleUsedItemResponse
import com.study.carrotmarket.constant.UsedItems
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

class RestApi {
    interface UsedArticleImpl {
        @Multipart
        @POST("/used/addUsedItem")
        fun setUsedArticle(
            @Part uploadImgs : List<MultipartBody.Part>
        ) : Single<String>

        @GET("/used/getAllUsedItem")
        fun getSimpleUsedItem() : Observable<List<SimpleUsedItemResponse>>

        @GET("/used/getDetailUsedItem")
        fun getDetailUsedItem() : Observable<List<DetailUsedItemResponse>>
    }

    companion object {
        fun setUsedArticle(content : List<MultipartBody.Part>) : Single<String> {
            return RetrofitCreator.create(
                UsedArticleImpl::class.java,
                RetrofitCreator.CARROTMARKET_API_BASE_URL
            ).setUsedArticle(content)
        }

        fun getSimpleUsedItem(): Observable<List<SimpleUsedItemResponse>> {
            return RetrofitCreator.create(
                UsedArticleImpl::class.java,
                RetrofitCreator.CARROTMARKET_API_BASE_URL
            ).getSimpleUsedItem()
        }

        fun getDetailUsedItem(): Observable<List<DetailUsedItemResponse>> {
            return RetrofitCreator.create(
                UsedArticleImpl::class.java,
                RetrofitCreator.CARROTMARKET_API_BASE_URL
            ).getDetailUsedItem()
        }
    }
}