package modeep.hear.infrastructure.config.webflux

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "internal.api")
data class InternalApiProperties(
    val baseUrl: String,
    val timeout: Long
)