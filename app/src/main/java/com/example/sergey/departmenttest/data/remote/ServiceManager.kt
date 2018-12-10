package com.example.sergey.departmenttest.data.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ServiceManager {
    val serviceApi: ServiceApi
}

class ServiceManagerImpl : ServiceManager {

    companion object {
        private const val BASE_URL = "https://contact.taxsee.com/Contacts.svc/"
    }

    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    override val serviceApi: ServiceApi
        get() = retrofit.create(ServiceApi::class.java)
}