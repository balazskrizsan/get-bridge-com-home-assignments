package com.getbridge.homework.tests.e2e.api.controllers.health

import com.getbridge.homework.api.config.HttpSecurityConfig
import com.getbridge.homework.api.controllers.health.Get403ForbiddenController
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(Get403ForbiddenController::class)
@ContextConfiguration(classes = [Get403ForbiddenController::class, HttpSecurityConfig::class])
class Http403Forbidden4Test {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `Forbidden endpoint returns 403 Forbidden`() {
        // Arrange
        val testedUrl = "/health/403Forbidden"

        val expectedStatus = MockMvcResultMatchers.status().isForbidden()
        val expectedData = ""

        // Act - Assert
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .get(testedUrl)
            )
            .andExpect(expectedStatus)
            .andExpect(MockMvcResultMatchers.content().string(expectedData))
    }
}
