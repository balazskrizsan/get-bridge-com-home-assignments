package com.getbridge.homework.api.services

import com.kbalazsworks.common.builders.ResponseEntityBuilder
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class GlobalExceptionHandlerService : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [Exception::class])
    @ResponseBody
    fun simpleErrorHandler(ex: Exception) = ResponseEntityBuilder<String>()
        .setData(ex.message)
        .setStatusCode(HttpStatus.BAD_REQUEST)
        .setErrorCode(1001)
        .build()
}
