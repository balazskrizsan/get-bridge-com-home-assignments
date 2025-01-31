package com.getbridge.homework.domain.oneonone_module.services

import com.getbridge.homework.domain.oneonone_module.entities.Participant
import com.getbridge.homework.domain.oneonone_module.repositories.ParticipantRepository
import org.springframework.stereotype.Service

@Service
class ParticipantService(private val participantRepository: ParticipantRepository) {

    fun createAll(participants: List<Participant>) {
        participantRepository.createAll(participants)
    }

    fun deleteByOneOnOneId(id: Long?) {
        participantRepository.deleteByOneOnOneId(id)
    }
}
