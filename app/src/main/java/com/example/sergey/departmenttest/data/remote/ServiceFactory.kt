package com.example.sergey.departmenttest.data.remote

import com.example.sergey.departmenttest.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceFactory {

    private const val BASE_URL = "https://contact.taxsee.com/Contacts.svc/"

    fun create(): ServiceApi {
        val logger = HttpLoggingInterceptor()
        logger.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
                .addNetworkInterceptor(logger)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(ServiceApi::class.java)
    }
}