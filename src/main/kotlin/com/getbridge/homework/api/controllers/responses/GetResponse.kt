package com.getbridge.homework.api.controllers.responses

data class GetResponse (
    val oneOnOne: OneOnOneResponse,
    val participants: List<ParticipantResponse>
)
