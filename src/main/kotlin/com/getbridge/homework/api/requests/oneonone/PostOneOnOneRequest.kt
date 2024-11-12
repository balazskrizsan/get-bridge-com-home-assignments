package com.getbridge.homework.api.requests.oneonone

data class PostOneOnOneRequest(
    val title: String,
    val plannedDate: String,
    val description: String,
    val location: String,
    var participants: List<Long>
)
