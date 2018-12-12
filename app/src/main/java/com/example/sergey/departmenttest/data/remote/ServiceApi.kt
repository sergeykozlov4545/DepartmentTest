package com.example.sergey.departmenttest.data.remote

import com.example.sergey.departmenttest.domain.model.Department
import com.example.sergey.departmenttest.domain.model.OperationStatus
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {
    @GET("Hello")
    fun hello(
            @Query("login") login: String,
            @Query("password") password: String
    ): Deferred<OperationStatus>

    @GET("GetAll")
    fun getDepartments(
            @Query("login") login: String,
            @Query("password") password: String
    ): Deferred<Department>

    @GET("GetWPhoto")
    fun getEmployeePhoto(
            @Query("login") login: String,
            @Query("password") password: String,
            @Query("id") employeeId: Long
    ): Deferred<ResponseBody>
}