package com.getbridge.homework.tests.e2e.api.controllers.health

import com.getbridge.homework.api.config.HttpSecurityConfig
import com.getbridge.homework.api.controllers.health.Get200OkAction
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(Get200OkAction::class)
@ContextConfiguration(classes = [Get200OkAction::class, HttpSecurityConfig::class])
class Get200OkActionTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Available endpoint returns 200 ok`() {
        // Arrange
        val testedUrl = "/health/200Ok"

        val expectedStatus = status().isOk()
        val expectedData = "OK"

        // Act - Assert
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .get(testedUrl)
            )
            .andExpect(expectedStatus)
            .andExpect(content().string(expectedData))
    }
}
