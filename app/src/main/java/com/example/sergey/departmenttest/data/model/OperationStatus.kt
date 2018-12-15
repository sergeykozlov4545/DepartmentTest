package com.example.sergey.departmenttest.data.model

import com.google.gson.annotations.SerializedName

data class OperationStatus(
        @SerializedName("Success") val isSuccess: Boolean = false,
        @SerializedName("Message") val message: String = ""
)