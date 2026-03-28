package modeep.hear.infrastructure.config.webflux

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "external.api")
data class ExternalApiProperties(
    val baseUrl: String,
    val timeout: Long
)
