package com.study.carrotmarket.model

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitCreator {
    companion object {
        /* Since July 2020, ip time domain has been restricted from issuing certification from CAA(Certificate Authority Authorization).
           The ddns.net domain uses a certificate obtained from letsencrypt.org
           PORT : http: 8080, https: 8443 */
        const val CARROTMARKET_API_BASE_URL = "https://csh0303.ddns.net:8443"

        private fun defaultRetrofit(base_url: String): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
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