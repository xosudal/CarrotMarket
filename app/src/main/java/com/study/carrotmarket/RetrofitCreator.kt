package com.study.carrotmarket

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitCreator {
    companion object {
        const val CARROTMARKET_API_BASE_URL = "http://csh0303.iptime.org:8080"

        private fun defaultRetrofit(base_url: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build()
        }

        fun <T> create(service: Class<T>, base_url : String): T {
            return defaultRetrofit(base_url).create(service)
        }

        private fun createOkHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()

            interceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .readTimeout(20, TimeUnit.MINUTES)
                .build()
        }
    }
}