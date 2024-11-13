package com.getbridge.homework.api.requests.oneonone

data class GetSearchRequest (
    val title: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val state: String? = null,
)
