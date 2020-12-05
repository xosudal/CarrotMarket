package com.study.carrotmarket.model

import com.study.carrotmarket.constant.DetailUsedItemResponse
import com.study.carrotmarket.constant.SimpleUsedItemResponse
import com.study.carrotmarket.constant.UsedItems
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

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
        fun getDetailUsedItem(@Query("_id") id : Int): Observable<List<DetailUsedItemResponse>>

        @POST("/user/registerUser") // nick name 변경 시 updateUser
        fun setUser(
            @Body body : RequestBody
        ) : Observable<String>
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

        fun getDetailUsedItem(id : Int): Observable<List<DetailUsedItemResponse>> {
            return RetrofitCreator.create(
                UsedArticleImpl::class.java,
                RetrofitCreator.CARROTMARKET_API_BASE_URL
            ).getDetailUsedItem(id)
        }

        fun setUser(body : RequestBody) : Observable<String> {
            return RetrofitCreator.create(
                UsedArticleImpl::class.java,
                RetrofitCreator.CARROTMARKET_API_BASE_URL
            ).setUser(body)
        }
    }
}