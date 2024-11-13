package com.getbridge.homework.tests.integration.domain.domain.oneonone_module.services

import com.getbridge.homework.domain.oneonone_module.entities.OneOnOne
import com.getbridge.homework.domain.oneonone_module.exceptions.OneOnOneException
import com.getbridge.homework.domain.oneonone_module.services.OneOnOneService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.Sql.ExecutionPhase
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode
import org.springframework.test.context.jdbc.SqlGroup
import java.time.LocalDateTime

@AutoConfigureMockMvc
@SpringBootTest
class OneOnOneServiceTest {

    @Autowired
    private lateinit var oneOnOneService: OneOnOneService

    @Test
    @SqlGroup(
        Sql(
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
            config = SqlConfig(transactionMode = TransactionMode.ISOLATED),
            scripts = [
                "classpath:test/sqls/_truncate_tables.sql",
                "classpath:test/sqls/one_on_ones_insert_1_record_concluded.sql",
                "classpath:test/sqls/participants_insert_1_record.sql",
            ]
        ),
        Sql(
            executionPhase = ExecutionPhase.AFTER_TEST_METHOD,
            config = SqlConfig(transactionMode = TransactionMode.ISOLATED),
            scripts = ["classpath:test/sqls/_truncate_tables.sql"]
        )
    )
    fun `update method concluded OneOnOne throws OneOnOneException`() {
        // Arrange
        val testedOneOnOne = OneOnOne(1, "tit", LocalDateTime.of(2020, 1, 2, 3, 4, 5), "des", "loc", null)

        // Act - Assert
        assertThrows<OneOnOneException> { oneOnOneService.update(testedOneOnOne, listOf()) }
    }
}
