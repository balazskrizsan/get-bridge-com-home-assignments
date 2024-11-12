package com.getbridge.homework.tests.e2e.api.controllers.oneonone

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
class GetActionTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

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
    fun `Valid fetch request returns 200 ok with OneOnOne entity and Participant entities`() {
        // Arrange
        val testedUrl = "/api/v1/one-on-one/1"

        val expectedStatus = status().isOk()
        val expectedData = """{"data":{"oneOnOne":{"id":1,"title":"tit","plannedDate":"2020-01-02T03:04:05","description":"des","location":"loc"},"participants":[{"oneOnOnesId":1,"employeeId":111},{"oneOnOnesId":1,"employeeId":222}]},"success":true,"errorCode":0,"requestId":""}"""

        // Act - Assert
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .get(testedUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(expectedStatus)
            .andExpect(content().string(expectedData))
    }
}
