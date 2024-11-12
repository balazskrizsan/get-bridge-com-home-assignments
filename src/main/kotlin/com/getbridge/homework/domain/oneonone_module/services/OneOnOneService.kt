package com.getbridge.homework.domain.oneonone_module.services

import com.getbridge.homework.domain.common_module.services.JooqService
import com.getbridge.homework.domain.oneonone_module.entities.OneOnOne
import com.getbridge.homework.domain.oneonone_module.entities.Participant
import com.getbridge.homework.domain.oneonone_module.repositories.OneOnOneRepository
import com.getbridge.homework.domain.oneonone_module.value_objects.OneOnOneWithParticipants
import org.jooq.Configuration
import org.springframework.stereotype.Service

@Service
class OneOnOneService(
    private val jooqService: JooqService,
    private val oneOnOneRepository: OneOnOneRepository,
    private val participantService: ParticipantService,
) {

    fun create(oneOnOne: OneOnOne, mapToParticipant: List<Participant>) {
        jooqService.dbContext?.transactionResult { _: Configuration? ->
            transactionalCreate(oneOnOne, mapToParticipant)
        }
    }

    private fun transactionalCreate(oneOnOne: OneOnOne, mapToParticipant: List<Participant>) {
        val newOneOnOne = oneOnOneRepository.create(oneOnOne)

        participantService.createAll(mapToParticipant.map { it.copy(oneOnOnesId = newOneOnOne.id) })
    }

    fun delete(id: Long) {
        oneOnOneRepository.delete(id)
    }

    fun get(id: Long): OneOnOneWithParticipants {
        return oneOnOneRepository.get(id)
    }

    fun update(oneOnOne: OneOnOne, participants: List<Participant>) {
        oneOnOneRepository.update(oneOnOne)
        participantService.deleteByOneOnOneId(oneOnOne.id)
        participantService.createAll(participants)
    }
}
