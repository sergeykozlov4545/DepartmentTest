package com.example.sergey.departmenttest.domain.model

import com.google.gson.annotations.SerializedName

data class OperationStatus(
        @SerializedName("Success") val isSuccess: Boolean = false,
        @SerializedName("Message") val message: String = ""
)