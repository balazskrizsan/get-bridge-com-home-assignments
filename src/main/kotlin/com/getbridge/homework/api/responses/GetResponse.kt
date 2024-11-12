package com.getbridge.homework.api.responses

data class GetResponse (
    val oneOnOne: OneOnOneResponse,
    val participants: List<ParticipantResponse>
)
