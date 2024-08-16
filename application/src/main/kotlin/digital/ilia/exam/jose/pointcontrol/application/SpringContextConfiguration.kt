package digital.ilia.exam.jose.pointcontrol.application

import digital.ilia.exam.jose.pointcontrol.business.BatidaService
import digital.ilia.exam.jose.pointcontrol.business.ports.input.BatidaServiceInputPort
import digital.ilia.exam.jose.pointcontrol.business.ports.output.BatidaRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringContextConfiguration {
    @Bean
    fun batidaServiceInputPort(batidaRepository: BatidaRepository): BatidaServiceInputPort {
        return BatidaService(batidaRepository)
    }
}
