package com.getbridge.homework.domain.oneonone_module.repositories

import com.getbridge.homework.db.tables.OneOnOnes
import com.getbridge.homework.db.tables.Participants
import com.getbridge.homework.domain.common_module.exceptions.RepositoryException
import com.getbridge.homework.domain.common_module.services.JooqService
import com.getbridge.homework.domain.oneonone_module.entities.OneOnOne
import com.getbridge.homework.domain.oneonone_module.entities.Participant
import com.getbridge.homework.domain.oneonone_module.value_objects.OneOnOneWithParticipants
import org.springframework.stereotype.Repository

@Repository
class OneOnOneRepository(private val jooqService: JooqService) {

    protected val oneOnOnesTable: OneOnOnes = OneOnOnes.ONE_ON_ONES
    protected val participantTable: Participants = Participants.PARTICIPANTS

    fun create(oneOnOne: OneOnOne): OneOnOne {
        val newRecord = jooqService.dbContext?.newRecord(oneOnOnesTable, oneOnOne)
        newRecord?.store()

        if (null == newRecord) {
            throw RepositoryException("Failed to create OneOnOne")
        }

        return newRecord.into(OneOnOne::class.java)
    }

    fun delete(id: Long) {
        jooqService.dbContext?.deleteFrom(oneOnOnesTable)?.where(oneOnOnesTable.ID.eq(id))?.execute()
    }

    fun get(id: Long): OneOnOneWithParticipants {
        val response = jooqService.dbContext
            ?.select(oneOnOnesTable.asterisk())
            ?.select(participantTable.asterisk())
            ?.from(oneOnOnesTable)
            ?.leftJoin(participantTable)?.on(oneOnOnesTable.ID.eq(participantTable.ONE_ON_ONES_ID))
            ?.where(oneOnOnesTable.ID.eq(id))
            ?.fetchGroups(oneOnOnesTable.ID)
            ?.map { (_, records) ->
                OneOnOneWithParticipants(
                    oneOnOne = records.first().into(OneOnOne::class.java),
                    participants = records.into(Participant::class.java),
                )
            }
            ?.first()

        if (null == response) {
            throw RepositoryException("OneOnOne record not found")
        }

        return response
    }
}
