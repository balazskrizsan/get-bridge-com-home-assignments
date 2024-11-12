package com.kbalazsworks.smartscrumpokerbackend.api.value_objects

data class ResponseData<T>(
    val data: T,
    val success: Boolean,
    val errorCode: Int,
    val requestId: String,
)
