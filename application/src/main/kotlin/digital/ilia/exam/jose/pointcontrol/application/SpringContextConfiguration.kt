package digital.ilia.exam.jose.pointcontrol.application

import digital.ilia.exam.jose.pointcontrol.business.BatidaService
import digital.ilia.exam.jose.pointcontrol.business.ports.input.BatidaServiceInputPort
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringContextConfiguration {
    @Bean
    fun batidaServiceInputPort(): BatidaServiceInputPort {
        return BatidaService()
    }
}
