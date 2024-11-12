package com.getbridge.homework.domain.oneonone_module.entities

import java.time.LocalDateTime

data class OneOnOne(
    val id: Long?,
    val title: String,
    val plannedDate: LocalDateTime,
    val description: String,
    val location: String,
)
