package com.getbridge.homework.migration

import org.flywaydb.core.api.migration.Context
import org.jooq.impl.DSL.constraint
import org.jooq.impl.DSL.currentTimestamp
import org.jooq.impl.SQLDataType.*
import org.springframework.stereotype.Component

@Component
class V000001__init : AbstractBaseJooqMigration() {
    override fun migrate(context: Context?) {
        val dslContext = getDslContext(context!!)

        dslContext.createTableIfNotExists("one_on_ones")
            .column("id", BIGINT.nullable(false).identity(true))
            .column("title", VARCHAR(255).nullable(false))
            .column("description", CLOB.nullable(false))
            .column("planned_date", TIMESTAMP.nullable(false))
            .column("location", VARCHAR(255))
            .column("created_at", TIMESTAMP.defaultValue(currentTimestamp()))
            .column("updated_at", TIMESTAMP.defaultValue(currentTimestamp()))
            .constraints(
                constraint("pk_one_on_ones_id").primaryKey("id")
            )
            .execute();

        dslContext.createTableIfNotExists("participants")
            .column("one_on_ones_id", BIGINT.nullable(false))
            .column("employee_id", BIGINT.nullable(false))
            .constraints(
                constraint("pk___one_on_ones_id___employee_id").primaryKey("one_on_ones_id", "employee_id"),
                constraint("fk___one_on_ones_id___one_on_ones__id")
                    .foreignKey("one_on_ones_id")
                    .references("one_on_ones", "id")
                    .onDeleteCascade()
            )
            .execute();
    }
}
