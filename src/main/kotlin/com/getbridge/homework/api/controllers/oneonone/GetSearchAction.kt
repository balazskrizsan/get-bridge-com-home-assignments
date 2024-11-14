package com.getbridge.homework.api.controllers.oneonone

import com.getbridge.homework.api.requests.oneonone.GetSearchRequest
import com.getbridge.homework.api.responses.GetSearchResponse
import com.getbridge.homework.api.services.RequestMapperService
import com.getbridge.homework.api.services.ResponseMapperService
import com.getbridge.homework.domain.oneonone_module.services.OneOnOneService
import com.getbridge.homework.domain.oneonone_module.value_objects.OneOnOneWithParticipants
import com.kbalazsworks.common.builders.ResponseEntityBuilder
import com.kbalazsworks.common.validators.JavaxValidatorService
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("OneOnOneController_GetSearchAction")
@RequestMapping("/api/v1/one-on-one")
class GetSearchAction(private val oneOnOneService: OneOnOneService) {

    @GetMapping(
        value = ["/search"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun action(
        @ModelAttribute request: GetSearchRequest,
        @RequestHeader("X-AUTHENTICATED-USER") userId: Long
    ): ResponseEntity<ResponseData<GetSearchResponse>> {
        JavaxValidatorService<GetSearchRequest>().validate(request)

        val response: List<OneOnOneWithParticipants> =
            oneOnOneService.search(RequestMapperService.mapToOneOnOneSearch(request), userId)

        return ResponseEntityBuilder<GetSearchResponse>()
            .setData(ResponseMapperService.mapToSearchResponse(response))
            .build()
    }
}
