package com.getbridge.homework.domain.common_module.services

import com.getbridge.homework.domain.common_module.exceptions.AuthException
import com.getbridge.homework.domain.oneonone_module.entities.Participant
import org.springframework.stereotype.Service

@Service
class AuthValidatorService {
    fun check(authenticatedUserId: Long, participants: List<Participant>) {
        if (!participants.any { it.employeeId == authenticatedUserId }) {
            throw AuthException("Authenticated user is not a participant")
        }
    }
}
