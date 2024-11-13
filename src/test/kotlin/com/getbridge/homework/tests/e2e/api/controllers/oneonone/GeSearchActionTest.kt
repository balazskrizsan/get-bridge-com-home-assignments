package com.getbridge.homework.tests.e2e.api.controllers.oneonone

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
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
import java.util.stream.Stream

@AutoConfigureMockMvc
@SpringBootTest
class GeSearchActionTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @ParameterizedTest
    @MethodSource("provider")
    @SqlGroup(
        Sql(
            executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
            config = SqlConfig(transactionMode = TransactionMode.ISOLATED),
            scripts = [
                "classpath:test/sqls/_truncate_tables.sql",
                "classpath:test/sqls/one_on_ones_insert_8_record.sql",
                "classpath:test/sqls/participants_insert_16_record.sql",
            ]
        ),
        Sql(
            executionPhase = ExecutionPhase.AFTER_TEST_METHOD,
            config = SqlConfig(transactionMode = TransactionMode.ISOLATED),
            scripts = ["classpath:test/sqls/_truncate_tables.sql"]
        )
    )
    fun `Multiple valid search request returns expected results by provider list`(
        queryParams: String,
        expectedData: String
    ) {
        // Arrange
        val testedUrl = "/api/v1/one-on-one/search?$queryParams"
        val expectedFullData = """{"data":$expectedData,"success":true,"errorCode":0,"requestId":""}"""

        val expectedStatus = status().isOk()

        // Act - Assert
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .get(testedUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(expectedStatus)
            .andExpect(content().string(expectedFullData))
    }

    companion object {
        @JvmStatic
        fun provider() = Stream.of(
            Arguments.of(
                "title=tit2",
                """{"oneOnOneWithParticipants":[{"oneOnOne":{"id":2,"title":"tit2","plannedDate":"2020-06-02T03:04:05","description":"des2","location":"loc","conclude":"2020-01-02T03:04:05"},"participants":[{"oneOnOnesId":2,"employeeId":111},{"oneOnOnesId":2,"employeeId":222}]}]}"""
            ),
            Arguments.of(
                "startDate=2022-01-01 00:00:00&state=open",
                """{"oneOnOneWithParticipants":[{"oneOnOne":{"id":5,"title":"tit5","plannedDate":"2022-01-02T03:04:05","description":"des5","location":"loc","conclude":null},"participants":[{"oneOnOnesId":5,"employeeId":111},{"oneOnOnesId":5,"employeeId":444}]},{"oneOnOne":{"id":7,"title":"tit7","plannedDate":"2023-01-02T03:04:05","description":"des7","location":"loc","conclude":null},"participants":[{"oneOnOnesId":7,"employeeId":666},{"oneOnOnesId":7,"employeeId":777}]}]}"""
            ),
            Arguments.of(
                "startDate=2022-01-01 00:00:00&state=closed",
                """{"oneOnOneWithParticipants":[{"oneOnOne":{"id":6,"title":"tit6","plannedDate":"2022-06-02T03:04:05","description":"des6","location":"loc","conclude":"2022-01-02T03:04:05"},"participants":[{"oneOnOnesId":6,"employeeId":111},{"oneOnOnesId":6,"employeeId":555}]},{"oneOnOne":{"id":8,"title":"tit8","plannedDate":"2023-06-02T03:04:05","description":"des8","location":"loc","conclude":"2023-01-02T03:04:05"},"participants":[{"oneOnOnesId":8,"employeeId":888},{"oneOnOnesId":8,"employeeId":999}]}]}"""
            ),
            Arguments.of(
                "endDate=2021-12-31 00:00:00&state=open",
                """{"oneOnOneWithParticipants":[{"oneOnOne":{"id":1,"title":"tit1","plannedDate":"2020-01-02T03:04:05","description":"des1","location":"loc","conclude":null},"participants":[{"oneOnOnesId":1,"employeeId":111},{"oneOnOnesId":1,"employeeId":222}]},{"oneOnOne":{"id":3,"title":"tit3","plannedDate":"2021-01-02T03:04:05","description":"des3","location":"loc","conclude":null},"participants":[{"oneOnOnesId":3,"employeeId":111},{"oneOnOnesId":3,"employeeId":333}]}]}"""
            ),
            Arguments.of(
                "endDate=2021-12-31 00:00:00&state=closed",
                """{"oneOnOneWithParticipants":[{"oneOnOne":{"id":2,"title":"tit2","plannedDate":"2020-06-02T03:04:05","description":"des2","location":"loc","conclude":"2020-01-02T03:04:05"},"participants":[{"oneOnOnesId":2,"employeeId":111},{"oneOnOnesId":2,"employeeId":222}]},{"oneOnOne":{"id":4,"title":"tit4","plannedDate":"2021-06-02T03:04:05","description":"des4","location":"loc","conclude":"2021-01-02T03:04:05"},"participants":[{"oneOnOnesId":4,"employeeId":111},{"oneOnOnesId":4,"employeeId":333}]}]}"""
            ),
            Arguments.of(
                "state=closed",
                """{"oneOnOneWithParticipants":[{"oneOnOne":{"id":2,"title":"tit2","plannedDate":"2020-06-02T03:04:05","description":"des2","location":"loc","conclude":"2020-01-02T03:04:05"},"participants":[{"oneOnOnesId":2,"employeeId":111},{"oneOnOnesId":2,"employeeId":222}]},{"oneOnOne":{"id":4,"title":"tit4","plannedDate":"2021-06-02T03:04:05","description":"des4","location":"loc","conclude":"2021-01-02T03:04:05"},"participants":[{"oneOnOnesId":4,"employeeId":111},{"oneOnOnesId":4,"employeeId":333}]},{"oneOnOne":{"id":6,"title":"tit6","plannedDate":"2022-06-02T03:04:05","description":"des6","location":"loc","conclude":"2022-01-02T03:04:05"},"participants":[{"oneOnOnesId":6,"employeeId":111},{"oneOnOnesId":6,"employeeId":555}]},{"oneOnOne":{"id":8,"title":"tit8","plannedDate":"2023-06-02T03:04:05","description":"des8","location":"loc","conclude":"2023-01-02T03:04:05"},"participants":[{"oneOnOnesId":8,"employeeId":888},{"oneOnOnesId":8,"employeeId":999}]}]}"""
            ),
            Arguments.of(
                "state=open",
                """{"oneOnOneWithParticipants":[{"oneOnOne":{"id":1,"title":"tit1","plannedDate":"2020-01-02T03:04:05","description":"des1","location":"loc","conclude":null},"participants":[{"oneOnOnesId":1,"employeeId":111},{"oneOnOnesId":1,"employeeId":222}]},{"oneOnOne":{"id":3,"title":"tit3","plannedDate":"2021-01-02T03:04:05","description":"des3","location":"loc","conclude":null},"participants":[{"oneOnOnesId":3,"employeeId":111},{"oneOnOnesId":3,"employeeId":333}]},{"oneOnOne":{"id":5,"title":"tit5","plannedDate":"2022-01-02T03:04:05","description":"des5","location":"loc","conclude":null},"participants":[{"oneOnOnesId":5,"employeeId":111},{"oneOnOnesId":5,"employeeId":444}]},{"oneOnOne":{"id":7,"title":"tit7","plannedDate":"2023-01-02T03:04:05","description":"des7","location":"loc","conclude":null},"participants":[{"oneOnOnesId":7,"employeeId":666},{"oneOnOnesId":7,"employeeId":777}]}]}"""
            ),
            Arguments.of("title=tit2&startDate=2023-01-01 00:00:00&endDate=2023-12-31 00:00:00&state=closed", """{"oneOnOneWithParticipants":[]}"""),
            Arguments.of("title=blablabla", """{"oneOnOneWithParticipants":[]}"""),
            Arguments.of(
                "",
                """{"oneOnOneWithParticipants":[{"oneOnOne":{"id":1,"title":"tit1","plannedDate":"2020-01-02T03:04:05","description":"des1","location":"loc","conclude":null},"participants":[{"oneOnOnesId":1,"employeeId":111},{"oneOnOnesId":1,"employeeId":222}]},{"oneOnOne":{"id":2,"title":"tit2","plannedDate":"2020-06-02T03:04:05","description":"des2","location":"loc","conclude":"2020-01-02T03:04:05"},"participants":[{"oneOnOnesId":2,"employeeId":111},{"oneOnOnesId":2,"employeeId":222}]},{"oneOnOne":{"id":3,"title":"tit3","plannedDate":"2021-01-02T03:04:05","description":"des3","location":"loc","conclude":null},"participants":[{"oneOnOnesId":3,"employeeId":111},{"oneOnOnesId":3,"employeeId":333}]},{"oneOnOne":{"id":4,"title":"tit4","plannedDate":"2021-06-02T03:04:05","description":"des4","location":"loc","conclude":"2021-01-02T03:04:05"},"participants":[{"oneOnOnesId":4,"employeeId":111},{"oneOnOnesId":4,"employeeId":333}]},{"oneOnOne":{"id":5,"title":"tit5","plannedDate":"2022-01-02T03:04:05","description":"des5","location":"loc","conclude":null},"participants":[{"oneOnOnesId":5,"employeeId":111},{"oneOnOnesId":5,"employeeId":444}]},{"oneOnOne":{"id":6,"title":"tit6","plannedDate":"2022-06-02T03:04:05","description":"des6","location":"loc","conclude":"2022-01-02T03:04:05"},"participants":[{"oneOnOnesId":6,"employeeId":111},{"oneOnOnesId":6,"employeeId":555}]},{"oneOnOne":{"id":7,"title":"tit7","plannedDate":"2023-01-02T03:04:05","description":"des7","location":"loc","conclude":null},"participants":[{"oneOnOnesId":7,"employeeId":666},{"oneOnOnesId":7,"employeeId":777}]},{"oneOnOne":{"id":8,"title":"tit8","plannedDate":"2023-06-02T03:04:05","description":"des8","location":"loc","conclude":"2023-01-02T03:04:05"},"participants":[{"oneOnOnesId":8,"employeeId":888},{"oneOnOnesId":8,"employeeId":999}]}]}"""
            ),
            Arguments.of(
                "title=tit&startDate=2022-01-01 00:00:00&endDate=2022-12-31 00:00:00",
                """{"oneOnOneWithParticipants":[{"oneOnOne":{"id":5,"title":"tit5","plannedDate":"2022-01-02T03:04:05","description":"des5","location":"loc","conclude":null},"participants":[{"oneOnOnesId":5,"employeeId":111},{"oneOnOnesId":5,"employeeId":444}]},{"oneOnOne":{"id":6,"title":"tit6","plannedDate":"2022-06-02T03:04:05","description":"des6","location":"loc","conclude":"2022-01-02T03:04:05"},"participants":[{"oneOnOnesId":6,"employeeId":111},{"oneOnOnesId":6,"employeeId":555}]}]}"""
            ),
            Arguments.of(
                "title=tit&startDate=2022-01-01 00:00:00&endDate=2022-12-31 00:00:00&state=closed",
                """{"oneOnOneWithParticipants":[{"oneOnOne":{"id":6,"title":"tit6","plannedDate":"2022-06-02T03:04:05","description":"des6","location":"loc","conclude":"2022-01-02T03:04:05"},"participants":[{"oneOnOnesId":6,"employeeId":111},{"oneOnOnesId":6,"employeeId":555}]}]}"""
            ),
        )
    }
}
