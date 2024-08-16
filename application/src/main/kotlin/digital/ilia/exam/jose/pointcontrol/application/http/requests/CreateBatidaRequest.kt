package digital.ilia.exam.jose.pointcontrol.application.http.requests

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateBatidaRequest(
    @JsonProperty("dataHora")
    val dateTime: String,
)
