package digital.ilia.exam.jose.pointcontrol.business.ports.output

import digital.ilia.exam.jose.pointcontrol.business.Batida
import java.time.LocalDate

interface BatidaRepository {
    fun save(batida: Batida)
    fun findAllByDate(toLocalDate: LocalDate): List<Batida>
}
