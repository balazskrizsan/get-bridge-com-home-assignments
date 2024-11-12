package com.getbridge.homework.tests.e2e.api.controllers.oneonone

import com.getbridge.homework.db.tables.OneOnOnes
import com.getbridge.homework.db.tables.Participants
import com.getbridge.homework.domain.common_module.services.JooqService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
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

@AutoConfigureMockMvc
@SpringBootTest
class DeleteActionTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var jooqService: JooqService

    protected val oneOnOnesTable: OneOnOnes = OneOnOnes.ONE_ON_ONES
    protected val participantTable: Participants = Participants.PARTICIPANTS

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
    fun `Valid delete request deletes from the db and returns 200 ok`() {
        // Arrange
        val testedUrl = "/api/v1/one-on-one/1"

        val expectedStatus = status().isOk()
        val expectedData = """{"data":null,"success":true,"errorCode":0,"requestId":""}"""
        val expectedOneOnOnesCount = 0
        val expectedParticipantCount = 0

        // Act - Assert
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .delete(testedUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(expectedStatus)
            .andExpect(content().string(expectedData))

        val actualOneOnOnesCount = jooqService.dbContext
            ?.selectCount()
            ?.from(oneOnOnesTable)
            ?.fetchOne(0, Int::class.java)

        val actualParticipantCount = jooqService.dbContext
            ?.selectCount()
            ?.from(participantTable)
            ?.fetchOne(0, Int::class.java)

        assertAll(
            { assertThat(actualOneOnOnesCount).isEqualTo(expectedOneOnOnesCount) },
            { assertThat(actualParticipantCount).isEqualTo(expectedParticipantCount) }
        )
    }
}
