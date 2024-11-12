package com.getbridge.homework.tests.e2e.api.controllers.oneonone

import com.getbridge.homework.db.tables.OneOnOnes
import com.getbridge.homework.db.tables.Participants
import com.getbridge.homework.domain.common_module.services.JooqService
import com.getbridge.homework.domain.oneonone_module.entities.OneOnOne
import com.getbridge.homework.domain.oneonone_module.entities.Participant
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
import java.time.LocalDateTime

@AutoConfigureMockMvc
@SpringBootTest
class PutActionTest {

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
    fun `Valid request updates the db and returns 200 ok`() {
        // Arrange
        val testedUrl = "/api/v1/one-on-one/1"

        val expectedStatus = status().isOk()
        val expectedData = """{"data":null,"success":true,"errorCode":0,"requestId":""}"""
        val expectedOneOnOnes = mutableListOf(
            OneOnOne(
                id = 1,
                title = "tit_mod",
                description = "des_mod",
                plannedDate = LocalDateTime.of(2025, 6, 7, 8, 9, 10),
                location = "loc_mod",
            )
        )
        val expectedParticipant = mutableListOf(
            Participant(oneOnOnesId = 1, employeeId = 222),
            Participant(oneOnOnesId = 1, employeeId = 333),
        )

        // Act - Assert
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .put(testedUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(
                        """
                            {
                                "title": "tit_mod",
                                "description": "des_mod",
                                "plannedDate": "2025-06-07 08:09:10",
                                "location": "loc_mod",
                                "participants": [222, 333]
                            }
                        """.trimIndent()
                    )
            )
            .andExpect(expectedStatus)
            .andExpect(content().string(expectedData))

        val actualOneOnOnes = jooqService.dbContext
            ?.selectFrom(oneOnOnesTable)
            ?.orderBy(oneOnOnesTable.ID.asc())
            ?.fetchInto(OneOnOne::class.java)

        val actualParticipant = jooqService.dbContext
            ?.selectFrom(participantTable)
            ?.orderBy(participantTable.ONE_ON_ONES_ID.asc())
            ?.fetchInto(Participant::class.java)

        assertAll(
            { assertThat(actualOneOnOnes).isEqualTo(expectedOneOnOnes) },
            { assertThat(actualParticipant).isEqualTo(expectedParticipant) }
        )
    }
}
