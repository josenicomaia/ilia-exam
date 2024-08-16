package digital.ilia.exam.jose.pointcontrol.business

import digital.ilia.exam.jose.pointcontrol.business.ports.input.BatidaServiceInputPort
import digital.ilia.exam.jose.pointcontrol.business.ports.output.BatidaRepository
import java.time.LocalDateTime
import java.util.*

class BatidaService(private val batidaRepository: BatidaRepository) : BatidaServiceInputPort {
    override fun record(dateTime: LocalDateTime): Batida {
        val recordsStack = batidaRepository.findAllByDate(dateTime.toLocalDate())

        if (recordsStack.count() >= 4) {
            throw DailyBatidaRecordLimitException()
        }

        validateIncompleteLunchtime(recordsStack, dateTime)
        // @TODO: validação de working day

        val batida = Batida(id = UUID.randomUUID(), dateTime = dateTime)
        batidaRepository.save(batida)

        return batida
    }

    private fun validateIncompleteLunchtime(
        recordsStack: Stack<Batida>,
        dateTime: LocalDateTime
    ) {
        if (recordsStack.count() == 2) {
            val lastRecord = recordsStack.peek()
            val lunchBreakEndDateTime = lastRecord.dateTime.plusHours(1)

            if (dateTime.isBefore(lunchBreakEndDateTime)) {
                throw RecordBatidaBeforeLunchBreakEndException()
            }
        }
    }
}
