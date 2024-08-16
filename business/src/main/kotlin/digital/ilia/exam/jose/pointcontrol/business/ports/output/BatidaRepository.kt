package digital.ilia.exam.jose.pointcontrol.business.ports.output

import digital.ilia.exam.jose.pointcontrol.business.Batida
import java.time.LocalDate
import java.util.*

interface BatidaRepository {
    fun save(batida: Batida)
    fun findAllByDate(toLocalDate: LocalDate): Stack<Batida>
}
