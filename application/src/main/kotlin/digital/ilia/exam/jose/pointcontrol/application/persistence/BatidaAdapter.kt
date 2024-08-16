package digital.ilia.exam.jose.pointcontrol.application.persistence

import digital.ilia.exam.jose.pointcontrol.application.persistence.models.BatidaJpa
import digital.ilia.exam.jose.pointcontrol.business.Batida
import org.springframework.stereotype.Service

@Service
class BatidaAdapter {
    fun toJpa(domain: Batida): BatidaJpa {
        return BatidaJpa(
            id = domain.id,
            date = domain.dateTime.toLocalDate(),
            dateTime = domain.dateTime,
        )
    }

    fun toDomain(batidaJpa: BatidaJpa): Batida {
        return Batida(
            id = batidaJpa.id,
            dateTime = batidaJpa.dateTime,
        )
    }
}
