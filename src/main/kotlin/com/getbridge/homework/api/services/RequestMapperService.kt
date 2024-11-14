package com.getbridge.homework.api.services

import com.getbridge.homework.api.requests.oneonone.GetSearchRequest
import com.getbridge.homework.api.requests.oneonone.PostRequest
import com.getbridge.homework.api.requests.oneonone.PutRequest
import com.getbridge.homework.domain.oneonone_module.entities.OneOnOne
import com.getbridge.homework.domain.oneonone_module.entities.Participant
import com.getbridge.homework.domain.oneonone_module.value_objects.OneOnOneSearch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RequestMapperService {
    companion object {
        fun mapToOneOnOne(request: PostRequest) = OneOnOne(
            id = null,
            title = request.title,
            plannedDate = createLocalDateTime(request.plannedDate),
            description = request.description,
            location = request.location,
            conclude = null,
        )

        fun mapToOneOnOne(request: PutRequest) = OneOnOne(
            id = request.id,
            title = request.title,
            plannedDate = createLocalDateTime(request.plannedDate),
            description = request.description,
            location = request.location,
            conclude = null,
        )

        fun mapToParticipant(request: PostRequest) =
            request.participants.map { Participant(oneOnOnesId = null, employeeId = it) }

        fun mapToParticipant(request: PutRequest, id: Long) =
            request.participants.map { Participant(oneOnOnesId = id, employeeId = it) }

        fun mapToOneOnOneSearch(request: GetSearchRequest) = OneOnOneSearch(
            title = request.title,
            startDate = request.startDate?.let { createLocalDateTime(it) },
            endDate = request.endDate?.let { createLocalDateTime(it) },
            isConcluded = when (request.state) {
                null -> null
                "closed" -> true
                else -> false
            }
        )

        private fun createLocalDateTime(dateTime: String): LocalDateTime {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            return LocalDateTime.parse(dateTime, formatter)
        }
    }
}
