package com.study.carrotmarket.model

import com.study.carrotmarket.constant.DetailUsedItemResponse
import com.study.carrotmarket.constant.SimpleUsedItemResponse
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

        @GET("/used/getAllUsedItems")
        fun getSimpleUsedItem(@Query("_timestamp") timnstmap : Int, @Query("_limit") limit : Int) : Observable<List<SimpleUsedItemResponse>>

        @GET("/used/getDetailUsedItem")
        fun getDetailUsedItem(@Query("_id") id : Int, @Query("_email") e_mail: String): Observable<DetailUsedItemResponse>

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
            ).getSimpleUsedItem(0, 5)
        }

        fun getDetailUsedItem(id : Int, e_mail : String): Observable<DetailUsedItemResponse> {
            return RetrofitCreator.create(
                UsedArticleImpl::class.java,
                RetrofitCreator.CARROTMARKET_API_BASE_URL
            ).getDetailUsedItem(id, e_mail)
        }

        fun setUser(body : RequestBody) : Observable<String> {
            return RetrofitCreator.create(
                UsedArticleImpl::class.java,
                RetrofitCreator.CARROTMARKET_API_BASE_URL
            ).setUser(body)
        }
    }
}