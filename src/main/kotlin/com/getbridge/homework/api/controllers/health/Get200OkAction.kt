package com.getbridge.homework.api.controllers.health

import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("HealthController_Get200OkController")
@RequestMapping("/health")
class Get200OkAction {

    @GetMapping("/200Ok")
    fun action() = ResponseEntity("OK", HttpStatusCode.valueOf(200))
}
