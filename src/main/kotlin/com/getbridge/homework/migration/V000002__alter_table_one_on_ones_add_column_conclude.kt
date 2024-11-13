package com.getbridge.homework.migration

import org.flywaydb.core.api.migration.Context
import org.jooq.impl.SQLDataType.TIMESTAMP
import org.springframework.stereotype.Component

@Component
class V000002__alter_table_one_on_ones_add_column_conclude : AbstractBaseJooqMigration() {
    override fun migrate(context: Context?) {
        val dslContext = getDslContext(context!!)

        dslContext.alterTable("one_on_ones")
            .addColumn("conclude", TIMESTAMP.nullable(true))
            // .after("location") not supported in PSQL
            .execute()
    }
}
