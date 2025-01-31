package com.getbridge.homework.domain.oneonone_module.services

import com.getbridge.homework.domain.common_module.services.AuthValidatorService
import com.getbridge.homework.domain.common_module.services.JooqService
import com.getbridge.homework.domain.oneonone_module.entities.OneOnOne
import com.getbridge.homework.domain.oneonone_module.entities.Participant
import com.getbridge.homework.domain.oneonone_module.exceptions.OneOnOneException
import com.getbridge.homework.domain.oneonone_module.repositories.OneOnOneRepository
import com.getbridge.homework.domain.oneonone_module.value_objects.OneOnOneSearch
import com.getbridge.homework.domain.oneonone_module.value_objects.OneOnOneWithParticipants
import org.jooq.Configuration
import org.springframework.stereotype.Service

@Service
class OneOnOneService(
    private val jooqService: JooqService,
    private val oneOnOneRepository: OneOnOneRepository,
    private val participantService: ParticipantService,
    private val authValidatorService: AuthValidatorService,
) {

    fun create(oneOnOne: OneOnOne, mapToParticipant: List<Participant>) =
        jooqService.dbContext.transactionResult { _: Configuration? ->
            transactionalCreate(oneOnOne, mapToParticipant)
        }

    private fun transactionalCreate(oneOnOne: OneOnOne, mapToParticipant: List<Participant>) {
        val newOneOnOne = oneOnOneRepository.create(oneOnOne)

        participantService.createAll(mapToParticipant.map { it.copy(oneOnOnesId = newOneOnOne.id) })
    }

    fun delete(id: Long, authenticatedUserId: Long) {
        val deletableOneOnOne = oneOnOneRepository.get(id)

        authValidatorService.check(authenticatedUserId, deletableOneOnOne.participants)

        oneOnOneRepository.delete(id)
    }

    fun get(id: Long, authenticatedUserId: Long): OneOnOneWithParticipants {
        val response = oneOnOneRepository.get(id)

        authValidatorService.check(authenticatedUserId, response.participants)

        return response
    }

    fun update(oneOnOne: OneOnOne, participants: List<Participant>, authenticatedUserId: Long) {
        val updatableOneOnOne = oneOnOne.id?.let { oneOnOneRepository.get(it) }

        updatableOneOnOne?.let { authValidatorService.check(authenticatedUserId, it.participants) }

        if (null != updatableOneOnOne?.oneOnOne?.conclude) {
            throw OneOnOneException("OneOnOne is already concluded")
        }

        oneOnOneRepository.update(oneOnOne)
        participantService.deleteByOneOnOneId(oneOnOne.id)
        participantService.createAll(participants)
    }

    fun conclude(id: Long, authenticatedUserId: Long) {
        val updatableOneOnOne = oneOnOneRepository.get(id)

        authValidatorService.check(authenticatedUserId, updatableOneOnOne.participants)

        oneOnOneRepository.conclude(id)
    }

    fun search(mapToOneOnOneSearch: OneOnOneSearch, authenticatedUserId: Long) =
        oneOnOneRepository.search(mapToOneOnOneSearch, authenticatedUserId)
}
