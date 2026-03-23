package modeep.hear.infrastructure.config.env

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = ["modeep.hear.infrastructure.adapter.out"])
@EnableJpaRepositories(
    basePackages = ["modeep.hear.infrastructure.adapter.out"],
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager"
)
@EnableJpaAuditing
class JpaConfig
