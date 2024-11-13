package com.getbridge.homework.api.services

import com.getbridge.homework.api.requests.oneonone.PostOneOnOneRequest
import com.getbridge.homework.api.requests.oneonone.PutRequest
import com.getbridge.homework.domain.oneonone_module.entities.OneOnOne
import com.getbridge.homework.domain.oneonone_module.entities.Participant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RequestMapperService {
    companion object {
        fun mapToOneOnOne(request: PostOneOnOneRequest) = OneOnOne(
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

        fun mapToParticipant(request: PostOneOnOneRequest) =
            request.participants.map { Participant(oneOnOnesId = null, employeeId = it) }

        private fun createLocalDateTime(dateTime: String): LocalDateTime {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            return LocalDateTime.parse(dateTime, formatter)
        }

        fun mapToParticipant(request: PutRequest, id: Long) =
            request.participants.map { Participant(oneOnOnesId = id, employeeId = it) }
    }
}
