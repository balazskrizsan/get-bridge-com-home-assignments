package com.getbridge.homework.tests.e2e.api.controllers.oneonone

import com.getbridge.homework.db.tables.OneOnOnes
import com.getbridge.homework.domain.common_module.providers.LocalDateTimeProvider
import com.getbridge.homework.domain.common_module.services.JooqService
import com.getbridge.homework.domain.oneonone_module.entities.OneOnOne
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.Sql.ExecutionPhase
import org.springframework.test.context.jdbc.SqlConfig
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@AutoConfigureMockMvc
@SpringBootTest
class PatchWithConcludeActionTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var jooqService: JooqService

    @MockBean
    private lateinit var localDateTimeProvider: LocalDateTimeProvider

    protected val oneOnOnesTable: OneOnOnes = OneOnOnes.ONE_ON_ONES

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
    fun `Valid conclude request updates the db and returns 200 ok`() {
        // Arrange
        val testedUrl = "/api/v1/one-on-one/1/conclude"
        val testedAuthenticatedUserId = 111L

        val expectedStatus = status().isOk()
        val expectedData = """{"data":null,"success":true,"errorCode":0,"requestId":""}"""
        val expectedOneOnOnes = OneOnOne(
            id = 1,
            title = "tit",
            description = "des",
            plannedDate = LocalDateTime.of(2020, 1, 2, 3, 4, 5),
            location = "loc",
            conclude = LocalDateTime.of(2025, 6, 7, 8, 9, 10),
        )

        Mockito.`when`(localDateTimeProvider.now())
            .thenReturn(LocalDateTime.of(2025, 6, 7, 8, 9, 10))

        // Act - Assert
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .patch(testedUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("X-AUTHENTICATED-USER", testedAuthenticatedUserId)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(expectedStatus)
            .andExpect(content().string(expectedData))

        val actualOneOnOnes = jooqService.dbContext
            .selectFrom(oneOnOnesTable)
            .fetchOneInto(OneOnOne::class.java)

        assertThat(actualOneOnOnes).isEqualTo(expectedOneOnOnes)
    }
}
