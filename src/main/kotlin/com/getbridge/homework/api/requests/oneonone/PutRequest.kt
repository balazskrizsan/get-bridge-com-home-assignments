package com.getbridge.homework.api.requests.oneonone

data class PutRequest(
    val id: Long?,
    val title: String,
    val plannedDate: String,
    val description: String,
    val location: String,
    var participants: List<Long>
)
