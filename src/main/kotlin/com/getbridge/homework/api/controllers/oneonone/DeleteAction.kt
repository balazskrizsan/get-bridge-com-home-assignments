package com.getbridge.homework.api.controllers.oneonone

import com.getbridge.homework.domain.oneonone_module.services.OneOnOneService
import com.kbalazsworks.common.builders.ResponseEntityBuilder
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("OneOnOneController_DeleteAction")
@RequestMapping("/api/v1/one-on-one")
class DeleteAction(private val oneOnOneService: OneOnOneService) {

    @DeleteMapping(
        value = ["/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun action(
        @PathVariable id: Long,
        @RequestHeader("X-AUTHENTICATED-USER") userId: Long
    ): ResponseEntity<ResponseData<String>> {

        oneOnOneService.delete(id, userId)

        return ResponseEntityBuilder<String>().setData(null).build()
    }
}
