package com.getbridge.homework.api.requests.oneonone

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.format.annotation.DateTimeFormat

data class PostOneOnOneRequest(
    @field:NotBlank(message = "Title is required")
    val title: String,
    @field:DateTimeFormat(pattern = "Required formatyyyy-MM-dd")
    @Valid
    val plannedDate: String,
    @field:NotBlank(message = "Description is required")
    val description: String,
    @field:NotBlank(message = "Location is required")
    val location: String,
    @field:Size(min = 2, max = 2, message = "Exactly 2 participants are required")
    var participants: List<Long>
)
