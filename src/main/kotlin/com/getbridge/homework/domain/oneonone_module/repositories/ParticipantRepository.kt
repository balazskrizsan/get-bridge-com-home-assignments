package com.getbridge.homework.domain.oneonone_module.repositories

import com.getbridge.homework.db.tables.Participants
import com.getbridge.homework.domain.common_module.services.JooqService
import com.getbridge.homework.domain.oneonone_module.entities.Participant
import org.jooq.impl.DSL.row
import org.springframework.stereotype.Repository

@Repository
class ParticipantRepository(val jooqService: JooqService) {

    protected val participantTable: Participants = Participants.PARTICIPANTS

    fun createAll(participants: List<Participant>) {
        jooqService.dbContext
            .insertInto(participantTable, participantTable.ONE_ON_ONES_ID, participantTable.EMPLOYEE_ID)
            .valuesOfRows(participants.map { row(it.oneOnOnesId, it.employeeId) })
            .execute()
    }

    fun deleteByOneOnOneId(id: Long?) {
        jooqService.dbContext
            .deleteFrom(participantTable)
            .where(participantTable.ONE_ON_ONES_ID.eq(id))
            .execute()
    }
}
