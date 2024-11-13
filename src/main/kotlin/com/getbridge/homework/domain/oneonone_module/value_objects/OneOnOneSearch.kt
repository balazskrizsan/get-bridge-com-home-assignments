package com.getbridge.homework.domain.oneonone_module.value_objects

import java.time.LocalDateTime

data class OneOnOneSearch(
    val title: String? = null,
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
    val isConcluded: Boolean? = null,
)
