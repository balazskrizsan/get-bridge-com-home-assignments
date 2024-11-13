package com.getbridge.homework.api.controllers.oneonone

import com.getbridge.homework.api.requests.oneonone.PostOneOnOneRequest
import com.getbridge.homework.api.services.RequestMapperService
import com.getbridge.homework.domain.oneonone_module.services.OneOnOneService
import com.kbalazsworks.common.builders.ResponseEntityBuilder
import com.kbalazsworks.common.validators.JavaxValidatorService
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("OneOnOneController_PostAction")
@RequestMapping("/api/v1/one-on-one")
class PostAction(private val oneOnOneService: OneOnOneService) {

    @PostMapping(
        value = ["/"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun action(@RequestBody request: PostOneOnOneRequest): ResponseEntity<ResponseData<String>> {
        JavaxValidatorService<PostOneOnOneRequest>().validate(request)

        oneOnOneService.create(
            RequestMapperService.mapToOneOnOne(request),
            RequestMapperService.mapToParticipant(request),
        )

        return ResponseEntityBuilder<String>().setData(null).build()
    }
}
