package digital.ilia.exam.jose.pointcontrol.application.http.controller

import digital.ilia.exam.jose.pointcontrol.application.http.requests.CreateBatidaRequest
import digital.ilia.exam.jose.pointcontrol.application.http.responses.CreateBatidaErrorResponse
import digital.ilia.exam.jose.pointcontrol.business.ports.input.BatidaServiceInputPort
import org.springframework.context.MessageSource
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.net.URI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

// z8n
@Controller("/batidas")
class BatidasController(
    private val batidaServiceInputPort: BatidaServiceInputPort,
    private val messageSource: MessageSource
) {
    companion object {
        private const val ERROR_MESSAGE_INVALID_DATE_TIME_FORMAT = "batida.dateTime.invalid"
    }

    @PostMapping
    fun createBatida(
        @RequestBody request: CreateBatidaRequest,
        locale: Locale
    ): ResponseEntity<CreateBatidaErrorResponse> {
        try {
            val localDateTime = LocalDateTime.parse(request.dateTime, DateTimeFormatter.ISO_DATE_TIME)
            val batida = batidaServiceInputPort.record(localDateTime)

            return ResponseEntity.created(URI.create("/batidas/${batida.id}"))
                .build()
        } catch (ex: DateTimeParseException) {
            return ResponseEntity.badRequest()
                .body(
                    CreateBatidaErrorResponse(
                        messageSource.getMessage(ERROR_MESSAGE_INVALID_DATE_TIME_FORMAT, null, locale)
                    )
                )
        }
    }
}
