package modeep.hear.infrastructure.config.webflux

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "external.api")
data class ExternalApiProperties(
    val baseUrl: String,
    val timeout: Duration
)
