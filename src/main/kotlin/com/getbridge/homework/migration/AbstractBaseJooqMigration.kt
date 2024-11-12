package com.getbridge.homework.migration

import org.flywaydb.core.api.migration.BaseJavaMigration
import org.flywaydb.core.api.migration.Context
import org.jooq.DSLContext
import org.jooq.impl.DSL.using

abstract class AbstractBaseJooqMigration : BaseJavaMigration() {
    protected fun getDslContext(context: Context): DSLContext {
        return using(context.connection)
    }
}
