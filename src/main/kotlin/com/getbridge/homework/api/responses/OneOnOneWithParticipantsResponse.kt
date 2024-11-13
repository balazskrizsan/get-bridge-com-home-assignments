package com.getbridge.homework.api.responses

data class OneOnOneWithParticipantsResponse (
    val oneOnOne: OneOnOneResponse,
    val participants: List<ParticipantResponse>
)
