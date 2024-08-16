package digital.ilia.exam.jose.pointcontrol.business

import java.time.LocalDateTime
import java.util.UUID

data class Batida(
    val id: UUID,
    val dateTime: LocalDateTime
)
