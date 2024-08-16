package digital.ilia.exam.jose.pointcontrol.business

import digital.ilia.exam.jose.pointcontrol.business.ports.input.BatidaServiceInputPort
import digital.ilia.exam.jose.pointcontrol.business.ports.output.BatidaRepository
import java.time.LocalDateTime
import java.util.*

class BatidaService(private val batidaRepository: BatidaRepository) : BatidaServiceInputPort {
    override fun record(dateTime: LocalDateTime): Batida {
        val batidaList = batidaRepository.findAllByDate(dateTime.toLocalDate())

        if (batidaList.count() >= 4) {
            throw BatidaRecordLimitException()
        }

        // validação de 1 hora de almoço
        // validação de working day

        val batida = Batida(id = UUID.randomUUID(), dateTime = dateTime)
        batidaRepository.save(batida)

        return batida
    }
}
