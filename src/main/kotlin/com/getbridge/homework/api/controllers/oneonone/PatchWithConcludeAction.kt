package com.getbridge.homework.api.controllers.oneonone

import com.getbridge.homework.domain.oneonone_module.services.OneOnOneService
import com.kbalazsworks.common.builders.ResponseEntityBuilder
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("OneOnOneController_PatchWithConcludeAction")
@RequestMapping("/api/v1/one-on-one")
class PatchWithConcludeAction(private val oneOnOneService: OneOnOneService) {

    @PatchMapping(
        value = ["/{id}/conclude"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun action(@PathVariable id: Long): ResponseEntity<ResponseData<String>> {

        oneOnOneService.conclude(id)

        return ResponseEntityBuilder<String>().setData(null).build()
    }
}
