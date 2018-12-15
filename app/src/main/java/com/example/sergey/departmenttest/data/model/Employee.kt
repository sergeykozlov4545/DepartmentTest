package com.example.sergey.departmenttest.data.model

import com.google.gson.annotations.SerializedName

data class Employee(
    @SerializedName("ID") val id: Long = 0,
    @SerializedName("Name") val name: String = "",
    @SerializedName("Title") val title: String = "",
    @SerializedName("Email") val email: String = "",
    @SerializedName("Phone") val phone: String = ""
)