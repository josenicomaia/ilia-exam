package digital.ilia.exam.jose.pointcontrol.business

import digital.ilia.exam.jose.pointcontrol.business.ports.output.BatidaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class BatidaServiceTest {
    private val batidaRepository: BatidaRepository = mockk()
    private val batidaService: BatidaService = BatidaService(batidaRepository)

    @Test
    fun givenAnDateTimeWhenRecordThenSaveIt() {
        every {
            batidaRepository.findAllByDate(LocalDate.parse("2024-08-16"))
        } returns Stack()

        every {
            batidaRepository.save(any<Batida>())
        } returns Unit

        val dateTime = LocalDateTime.parse("2024-08-16T10:00:00")

        val batida = batidaService.record(dateTime)

        assertNotNull(batida.id)
        assertEquals(dateTime, batida.dateTime)

        verify { batidaRepository.findAllByDate(LocalDate.parse("2024-08-16")) }
        verify { batidaRepository.save(any<Batida>()) }
    }

    @Test
    fun givenAnAlreadyRecordedFourTimesWhenRecordThenThrowException() {
        every {
            batidaRepository.findAllByDate(LocalDate.parse("2024-08-16"))
        } returns Stack<Batida>().apply {
            push(Batida(UUID.randomUUID(), LocalDateTime.parse("2024-08-16T08:00")))
            push(Batida(UUID.randomUUID(), LocalDateTime.parse("2024-08-16T08:00")))
            push(Batida(UUID.randomUUID(), LocalDateTime.parse("2024-08-16T08:00")))
            push(Batida(UUID.randomUUID(), LocalDateTime.parse("2024-08-16T08:00")))
        }

        val dateTime = LocalDateTime.parse("2024-08-16T10:00:00")

        assertThrows<DailyBatidaRecordLimitException> {
            batidaService.record(dateTime)
        }
    }

    @Test
    fun givenAnAlreadyRecordedLeavingForLunchWhenRecordBeforeOneHourBreakThenThrowException() {
        every {
            batidaRepository.findAllByDate(LocalDate.parse("2024-08-16"))
        } returns Stack<Batida>().apply {
            push(Batida(UUID.randomUUID(), LocalDateTime.parse("2024-08-16T08:00")))
            push(Batida(UUID.randomUUID(), LocalDateTime.parse("2024-08-16T08:00")))
        }

        val dateTime = LocalDateTime.parse("2024-08-16T08:00:00")

        assertThrows<RecordBatidaBeforeLunchBreakEndException> {
            batidaService.record(dateTime)
        }
    }
}
