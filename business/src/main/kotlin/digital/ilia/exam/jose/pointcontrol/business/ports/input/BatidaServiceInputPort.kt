package digital.ilia.exam.jose.pointcontrol.business.ports.input

import digital.ilia.exam.jose.pointcontrol.business.Batida
import java.time.LocalDateTime

interface BatidaServiceInputPort {
    fun record(dateTime: LocalDateTime): Batida
}
