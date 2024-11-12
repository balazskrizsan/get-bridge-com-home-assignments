package com.getbridge.homework.domain.oneonone_module.repositories

import com.getbridge.homework.db.tables.OneOnOnes
import com.getbridge.homework.domain.common_module.exceptions.RepositoryException
import com.getbridge.homework.domain.common_module.services.JooqService
import com.getbridge.homework.domain.oneonone_module.entities.OneOnOne
import org.springframework.stereotype.Repository

@Repository
class OneOnOneRepository(val jooqService: JooqService) {

    protected val oneOnOnesTable: OneOnOnes = OneOnOnes.ONE_ON_ONES

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
}
