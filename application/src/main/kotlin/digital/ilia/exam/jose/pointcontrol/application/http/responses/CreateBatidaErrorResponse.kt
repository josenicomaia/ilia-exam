package digital.ilia.exam.jose.pointcontrol.application.http.responses

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateBatidaErrorResponse(
    @JsonProperty("mensagem")
    val message: String
)
