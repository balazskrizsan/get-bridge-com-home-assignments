package com.getbridge.homework.api.controllers.oneonone

import com.getbridge.homework.api.requests.oneonone.PutRequest
import com.getbridge.homework.api.services.RequestMapperService
import com.getbridge.homework.domain.oneonone_module.services.OneOnOneService
import com.kbalazsworks.common.builders.ResponseEntityBuilder
import com.kbalazsworks.common.validators.JavaxValidatorService
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController("OneOnOneController_PutAction")
@RequestMapping("/api/v1/one-on-one")
class PutAction(private val oneOnOneService: OneOnOneService) {

    @PutMapping(
        value = ["/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun action(
        @RequestBody request: PutRequest,
        @PathVariable id: Long
    ): ResponseEntity<ResponseData<String>> {

        JavaxValidatorService<PutRequest>().validate(request)

        oneOnOneService.update(
            RequestMapperService.mapToOneOnOne(request.copy(id = id)),
            RequestMapperService.mapToParticipant(request, id),
        )

        return ResponseEntityBuilder<String>().setData(null).build()
    }
}
