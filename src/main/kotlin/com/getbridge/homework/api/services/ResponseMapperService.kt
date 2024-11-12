package com.getbridge.homework.api.services

import com.getbridge.homework.api.controllers.responses.GetResponse
import com.getbridge.homework.api.controllers.responses.OneOnOneResponse
import com.getbridge.homework.api.controllers.responses.ParticipantResponse
import com.getbridge.homework.domain.oneonone_module.entities.OneOnOne
import com.getbridge.homework.domain.oneonone_module.entities.Participant
import com.getbridge.homework.domain.oneonone_module.value_objects.OneOnOneWithParticipants

class ResponseMapperService {
    companion object {
        fun mapToGetResponse(oneOnOneWithParticipants: OneOnOneWithParticipants) =
            GetResponse(
                oneOnOne = mapToOneOnOneResponse(oneOnOneWithParticipants.oneOnOne),
                participants = mapToParticipantResponse(oneOnOneWithParticipants.participants)
            )

        private fun mapToOneOnOneResponse(oneOnOne: OneOnOne) =
            OneOnOneResponse(
                id = oneOnOne.id,
                title = oneOnOne.title,
                plannedDate = oneOnOne.plannedDate,
                description = oneOnOne.description,
                location = oneOnOne.location,
            )

        private fun mapToParticipantResponse(participants: List<Participant>) =
            participants.map {
                ParticipantResponse(
                    oneOnOnesId = it.oneOnOnesId,
                    employeeId = it.employeeId,
                )
            }
    }
}
