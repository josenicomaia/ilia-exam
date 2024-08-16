package digital.ilia.exam.jose.pointcontrol.application.persistence.repositories

import digital.ilia.exam.jose.pointcontrol.application.persistence.models.BatidaJpa
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate
import java.util.*

interface SpringDataBatidaRepository : CrudRepository<BatidaJpa, UUID> {
    fun findAllByDate(date: LocalDate): Stack<BatidaJpa>
}
