package com.getbridge.homework.domain.common_module.services

import com.getbridge.homework.common.services.ApplicationPropertiesService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.TransactionalRunnable
import org.jooq.impl.DSL
import org.springframework.stereotype.Service

@Service
class JooqService(val ap: ApplicationPropertiesService) {
    val dbContext: DSLContext?
        get() {
            if (null == dslContext) {
                val config = HikariConfig()
                config.transactionIsolation = "TRANSACTION_READ_UNCOMMITTED"
                config.jdbcUrl = ap.springDatasourceUrl
                config.username = "admin"
                config.password = ap.springDatasourcePassword
                config.maximumPoolSize = 10
                config.addDataSourceProperty("cachePrepStmts", "true")
                config.addDataSourceProperty("prepStmtCacheSize", "250")
                config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")

                dslContext = DSL.using(HikariDataSource(config), SQLDialect.POSTGRES)
            }

            return dslContext
        }

    fun transaction(transactional: TransactionalRunnable) {
        dbContext!!.transaction(transactional)
    }

    companion object {
        private var dslContext: DSLContext? = null
    }
}
