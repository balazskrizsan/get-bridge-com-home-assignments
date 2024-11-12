package com.getbridge.homework.domain.oneonone_module.value_objects

import com.getbridge.homework.domain.oneonone_module.entities.OneOnOne
import com.getbridge.homework.domain.oneonone_module.entities.Participant

class OneOnOneWithParticipants (
    val oneOnOne: OneOnOne,
    val participants: List<Participant>,
)
