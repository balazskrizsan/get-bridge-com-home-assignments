package com.getbridge.homework.api.controllers.responses

import java.time.LocalDateTime

data class OneOnOneResponse(
    val id: Long?,
    val title: String,
    val plannedDate: LocalDateTime,
    val description: String,
    val location: String,
)
