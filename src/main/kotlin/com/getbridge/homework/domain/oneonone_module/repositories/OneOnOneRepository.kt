package com.getbridge.homework.domain.oneonone_module.repositories

import com.getbridge.homework.db.tables.OneOnOnes
import com.getbridge.homework.db.tables.Participants
import com.getbridge.homework.domain.common_module.exceptions.RepositoryException
import com.getbridge.homework.domain.common_module.providers.LocalDateTimeProvider
import com.getbridge.homework.domain.common_module.services.JooqService
import com.getbridge.homework.domain.oneonone_module.entities.OneOnOne
import com.getbridge.homework.domain.oneonone_module.entities.Participant
import com.getbridge.homework.domain.oneonone_module.value_objects.OneOnOneSearch
import com.getbridge.homework.domain.oneonone_module.value_objects.OneOnOneWithParticipants
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
class OneOnOneRepository(
    private val jooqService: JooqService,
    private val localDateTimeProvider: LocalDateTimeProvider
) {

    protected val oneOnOnesTable: OneOnOnes = OneOnOnes.ONE_ON_ONES
    protected val participantTable: Participants = Participants.PARTICIPANTS

    fun create(oneOnOne: OneOnOne): OneOnOne {
        val newRecord = jooqService.dbContext.newRecord(oneOnOnesTable, oneOnOne)
        newRecord.store()

        return newRecord.into(OneOnOne::class.java)
    }

    fun delete(id: Long) {
        jooqService.dbContext.deleteFrom(oneOnOnesTable).where(oneOnOnesTable.ID.eq(id)).execute()
    }

    fun get(id: Long): OneOnOneWithParticipants {
        val response: OneOnOneWithParticipants?
        try {
            response = jooqService.dbContext
                .select(oneOnOnesTable.asterisk())
                .select(participantTable.asterisk())
                .from(oneOnOnesTable)
                .leftJoin(participantTable).on(oneOnOnesTable.ID.eq(participantTable.ONE_ON_ONES_ID))
                .where(oneOnOnesTable.ID.eq(id))
                .fetchGroups(oneOnOnesTable.ID)
                .map { (_, records) ->
                    OneOnOneWithParticipants(
                        oneOnOne = records.first().into(OneOnOne::class.java),
                        participants = records.into(Participant::class.java),
                    )
                }
                .first()
        } catch (e: NoSuchElementException) {
            throw RepositoryException("OneOnOne record not found")
        }

        return response
    }

    fun update(oneOnOne: OneOnOne) {
        jooqService.dbContext
            .update(oneOnOnesTable)
            .set(oneOnOnesTable.TITLE, oneOnOne.title)
            .set(oneOnOnesTable.PLANNED_DATE, oneOnOne.plannedDate)
            .set(oneOnOnesTable.DESCRIPTION, oneOnOne.description)
            .set(oneOnOnesTable.LOCATION, oneOnOne.location)
            .where(oneOnOnesTable.ID.eq(oneOnOne.id))
            .execute()
    }

    fun conclude(id: Long) {
        jooqService.dbContext
            .update(oneOnOnesTable)
            .set(oneOnOnesTable.CONCLUDE, localDateTimeProvider.now())
            .where(oneOnOnesTable.ID.eq(id))
            .execute()
    }

    fun search(mapToOneOnOneSearch: OneOnOneSearch, authenticatedUserId: Long): List<OneOnOneWithParticipants> {
        val ctx = jooqService.dbContext

        val prefilter = ctx.select(participantTable.ONE_ON_ONES_ID, DSL.count().`as`("count"))
            .from(participantTable)
            .where(participantTable.EMPLOYEE_ID.eq(authenticatedUserId))
            .groupBy(participantTable.ONE_ON_ONES_ID)
            .asTable("filter")

        // @formatter:off
        val query = ctx.select(DSL.field("count"))
            .select(oneOnOnesTable.asterisk())
            .select(participantTable.asterisk())
            .from(oneOnOnesTable)
            .leftJoin(prefilter)
                .on(oneOnOnesTable.ID.eq(prefilter.field("one_on_ones_id", oneOnOnesTable.ID.dataType)))
            .leftJoin(participantTable)
                .on(oneOnOnesTable.ID.eq(participantTable.ONE_ON_ONES_ID))
        // @formatter:on

        if (null != mapToOneOnOneSearch.title) {
            query.where(oneOnOnesTable.TITLE.contains(mapToOneOnOneSearch.title))
        }

        if (null != mapToOneOnOneSearch.startDate) {
            query.where(oneOnOnesTable.PLANNED_DATE.greaterOrEqual(mapToOneOnOneSearch.startDate))
        }

        if (null != mapToOneOnOneSearch.endDate) {
            query.where(oneOnOnesTable.PLANNED_DATE.lessOrEqual(mapToOneOnOneSearch.endDate))
        }

        if (true == mapToOneOnOneSearch.isConcluded) {
            query.where(oneOnOnesTable.CONCLUDE.isNotNull())
        } else if (false == mapToOneOnOneSearch.isConcluded) {
            query.where(oneOnOnesTable.CONCLUDE.isNull())
        }

        query.where(DSL.field("filter.count").greaterThan(0))

        return query.fetchGroups(oneOnOnesTable.ID)
            .map { (_, records) ->
                OneOnOneWithParticipants(
                    oneOnOne = records.first().into(OneOnOne::class.java),
                    participants = records.into(Participant::class.java),
                )
            }
    }
}
