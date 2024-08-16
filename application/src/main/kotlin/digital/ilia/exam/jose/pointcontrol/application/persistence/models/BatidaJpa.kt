package digital.ilia.exam.jose.pointcontrol.application.persistence.models

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@RedisHash("batidas")
data class BatidaJpa(
    @Id
    val id: UUID,

    @Indexed
    val date: LocalDate,

    val dateTime: LocalDateTime
)
