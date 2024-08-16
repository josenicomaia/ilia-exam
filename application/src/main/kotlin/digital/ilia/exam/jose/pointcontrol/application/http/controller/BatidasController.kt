package digital.ilia.exam.jose.pointcontrol.application.http.controller

import digital.ilia.exam.jose.pointcontrol.application.http.requests.CreateBatidaRequest
import digital.ilia.exam.jose.pointcontrol.application.http.responses.CreateBatidaErrorResponse
import digital.ilia.exam.jose.pointcontrol.business.DailyBatidaRecordLimitException
import digital.ilia.exam.jose.pointcontrol.business.RecordBatidaBeforeLunchBreakEndException
import digital.ilia.exam.jose.pointcontrol.business.ports.input.BatidaServiceInputPort
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

// z8n
@RestController
@RequestMapping("/batidas")
class BatidasController(
    private val batidaServiceInputPort: BatidaServiceInputPort,
    private val messageSource: MessageSource
) {
    companion object {
        private const val ERROR_MESSAGE_INVALID_DATE_TIME_FORMAT = "batida.dateTime.invalid"
        private const val ERROR_MESSAGE_DAILY_RECORD_LIMIT = "batida.daily.limit"
        private const val ERROR_MESSAGE_RECORD_BEFORE_LUNCH_BREAK_ENDS = "batida.before.lunch.break.ends"
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
        } catch (ex: DailyBatidaRecordLimitException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                    CreateBatidaErrorResponse(
                        messageSource.getMessage(ERROR_MESSAGE_DAILY_RECORD_LIMIT, null, locale)
                    )
                )
        } catch (ex: RecordBatidaBeforeLunchBreakEndException) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                    CreateBatidaErrorResponse(
                        messageSource.getMessage(ERROR_MESSAGE_RECORD_BEFORE_LUNCH_BREAK_ENDS, null, locale)
                    )
                )
        }
    }
}
