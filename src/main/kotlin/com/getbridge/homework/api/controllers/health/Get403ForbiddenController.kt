package com.getbridge.homework.api.controllers.health

import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("HealtControllerGet403ForbiddenController")
@RequestMapping("/health")
class Get403ForbiddenController {

    @GetMapping("/403Forbidden")
    fun action() = ResponseEntity("Must be blocked by Http Security", HttpStatusCode.valueOf(200))
}
