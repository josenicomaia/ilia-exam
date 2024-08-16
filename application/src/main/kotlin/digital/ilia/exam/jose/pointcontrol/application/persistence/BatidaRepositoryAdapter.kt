package digital.ilia.exam.jose.pointcontrol.application.persistence

import digital.ilia.exam.jose.pointcontrol.application.persistence.repositories.SpringDataBatidaRepository
import digital.ilia.exam.jose.pointcontrol.business.Batida
import digital.ilia.exam.jose.pointcontrol.business.ports.output.BatidaRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*

@Service
class BatidaRepositoryAdapter(
    private val springDataBatidaRepository: SpringDataBatidaRepository,
    private val batidaAdapter: BatidaAdapter,
) : BatidaRepository {
    override fun save(batida: Batida) {
        val batidaJpa = batidaAdapter.toJpa(batida)
        springDataBatidaRepository.save(batidaJpa)
    }

    override fun findAllByDate(date: LocalDate): Stack<Batida> {
        return Stack<Batida>().apply {
            addAll(springDataBatidaRepository.findAllByDate(
                date
            ).map {
                batidaAdapter.toDomain(it)
            })
        }
    }
}
