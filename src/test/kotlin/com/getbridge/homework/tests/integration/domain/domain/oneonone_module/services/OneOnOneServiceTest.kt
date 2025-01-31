package com.getbridge.homework.tests.integration.domain.domain.oneonone_module.services

import com.getbridge.homework.domain.common_module.exceptions.AuthException
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
    fun `update method Concluded OneOnOne throws OneOnOneException`() {
        // Arrange
        val testedOneOnOne = OneOnOne(1, "tit", LocalDateTime.of(2020, 1, 2, 3, 4, 5), "des", "loc", null)
        val testedAuthenticatedUserId = 222L

        // Act - Assert
        assertThrows<OneOnOneException> { oneOnOneService.update(testedOneOnOne, listOf(), testedAuthenticatedUserId) }
    }

    @Test
    @SqlGroup(
        Sql(
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
            config = SqlConfig(transactionMode = TransactionMode.ISOLATED),
            scripts = [
                "classpath:test/sqls/_truncate_tables.sql",
                "classpath:test/sqls/one_on_ones_insert_1_record.sql",
                "classpath:test/sqls/participants_insert_1_record.sql",
            ]
        ),
        Sql(
            executionPhase = ExecutionPhase.AFTER_TEST_METHOD,
            config = SqlConfig(transactionMode = TransactionMode.ISOLATED),
            scripts = ["classpath:test/sqls/_truncate_tables.sql"]
        )
    )
    fun `update method OneOnOne with not unauthenticated user throws AuthException`() {
        // Arrange
        val testedOneOnOne = OneOnOne(1, "tit", LocalDateTime.of(2020, 1, 2, 3, 4, 5), "des", "loc", null)
        val testedAuthenticatedUserId = 123L

        // Act - Assert
        assertThrows<AuthException> { oneOnOneService.update(testedOneOnOne, listOf(), testedAuthenticatedUserId) }
    }

    @Test
    @SqlGroup(
        Sql(
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
            config = SqlConfig(transactionMode = TransactionMode.ISOLATED),
            scripts = [
                "classpath:test/sqls/_truncate_tables.sql",
                "classpath:test/sqls/one_on_ones_insert_1_record.sql",
                "classpath:test/sqls/participants_insert_1_record.sql",
            ]
        ),
        Sql(
            executionPhase = ExecutionPhase.AFTER_TEST_METHOD,
            config = SqlConfig(transactionMode = TransactionMode.ISOLATED),
            scripts = ["classpath:test/sqls/_truncate_tables.sql"]
        )
    )
    fun `delete method Deleting with not unauthenticated user throws AuthException`() {
        // Arrange
        val testedOneOnOneId = 1L
        val testedAuthenticatedUserId = 123L

        // Act - Assert
        assertThrows<AuthException> { oneOnOneService.delete(testedOneOnOneId, testedAuthenticatedUserId) }
    }

    @Test
    @SqlGroup(
        Sql(
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
            config = SqlConfig(transactionMode = TransactionMode.ISOLATED),
            scripts = [
                "classpath:test/sqls/_truncate_tables.sql",
                "classpath:test/sqls/one_on_ones_insert_1_record.sql",
                "classpath:test/sqls/participants_insert_1_record.sql",
            ]
        ),
        Sql(
            executionPhase = ExecutionPhase.AFTER_TEST_METHOD,
            config = SqlConfig(transactionMode = TransactionMode.ISOLATED),
            scripts = ["classpath:test/sqls/_truncate_tables.sql"]
        )
    )
    fun `conclude method Conclude with not unauthenticated user throws AuthException`() {
        // Arrange
        val testedOneOnOneId = 1L
        val testedAuthenticatedUserId = 123L

        // Act - Assert
        assertThrows<AuthException> { oneOnOneService.conclude(testedOneOnOneId, testedAuthenticatedUserId) }
    }

    @Test
    @SqlGroup(
        Sql(
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
            config = SqlConfig(transactionMode = TransactionMode.ISOLATED),
            scripts = [
                "classpath:test/sqls/_truncate_tables.sql",
                "classpath:test/sqls/one_on_ones_insert_1_record.sql",
                "classpath:test/sqls/participants_insert_1_record.sql",
            ]
        ),
        Sql(
            executionPhase = ExecutionPhase.AFTER_TEST_METHOD,
            config = SqlConfig(transactionMode = TransactionMode.ISOLATED),
            scripts = ["classpath:test/sqls/_truncate_tables.sql"]
        )
    )
    fun `get method testedAuthenticatedUserId is in response returns owners one on one`() {
        // Arrange
        val testedId = 1L
        val testedAuthenticatedUserId = 111L

        // Act
        oneOnOneService.get(testedId, testedAuthenticatedUserId)
    }

    @Test
    @SqlGroup(
        Sql(
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
            config = SqlConfig(transactionMode = TransactionMode.ISOLATED),
            scripts = [
                "classpath:test/sqls/_truncate_tables.sql",
                "classpath:test/sqls/one_on_ones_insert_1_record.sql",
                "classpath:test/sqls/participants_insert_1_record.sql",
            ]
        ),
        Sql(
            executionPhase = ExecutionPhase.AFTER_TEST_METHOD,
            config = SqlConfig(transactionMode = TransactionMode.ISOLATED),
            scripts = ["classpath:test/sqls/_truncate_tables.sql"]
        )
    )
    fun `get method testedAuthenticatedUserId is not in response throws exception`() {
        // Arrange
        val testedId = 1L
        val testedAuthenticatedUserId = 123L

        // Act
        assertThrows<AuthException> { oneOnOneService.get(testedId, testedAuthenticatedUserId) }
    }
}
