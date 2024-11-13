package com.getbridge.homework.domain.common_module.providers

import org.springframework.stereotype.Component

@Component
class LocalDateTimeProvider {
    fun now() = java.time.LocalDateTime.now()
}
