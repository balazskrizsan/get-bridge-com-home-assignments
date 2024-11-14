package com.getbridge.homework.migration

import org.flywaydb.core.api.migration.Context
import org.springframework.stereotype.Component

@Component
class V000003__create_indexes : AbstractBaseJooqMigration() {
    override fun migrate(context: Context?) {
        val ctx = getDslContext(context!!)

        ctx.createIndex("idx___one_on_ones__conclude")
            .on("one_on_ones", "conclude")
            .execute()

        ctx.createIndex("idx___one_on_ones__title")
            .on("one_on_ones", "title")
            .execute()

        ctx.createIndex("idx___one_on_ones__planned_date")
            .on("one_on_ones", "planned_date")
            .execute()

        ctx.createIndex("idx___participants__employee_id")
            .on("participants", "employee_id")
            .execute()
    }
}
