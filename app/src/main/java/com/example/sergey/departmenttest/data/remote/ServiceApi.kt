package com.example.sergey.departmenttest.data.remote

import com.example.sergey.departmenttest.domain.model.OperationStatus
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {
    @GET("Hello")
    fun hello(@Query("login") login: String, @Query("password") password: String): Deferred<OperationStatus>
}