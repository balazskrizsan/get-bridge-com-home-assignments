package com.getbridge.homework.api.controllers.oneonone

import com.getbridge.homework.api.controllers.responses.GetResponse
import com.getbridge.homework.api.services.ResponseMapperService
import com.getbridge.homework.domain.oneonone_module.services.OneOnOneService
import com.kbalazsworks.common.builders.ResponseEntityBuilder
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("OneOnOneController_GetAction")
@RequestMapping("/api/v1/one-on-one")
class GetAction(private val oneOnOneService: OneOnOneService) {

    @GetMapping(
        value = ["/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun action(@PathVariable id: Long): ResponseEntity<ResponseData<GetResponse>> {
        val response = oneOnOneService.get(id)

        return ResponseEntityBuilder<GetResponse>()
            .setData(ResponseMapperService.mapToGetResponse(response))
            .build()
    }
}
