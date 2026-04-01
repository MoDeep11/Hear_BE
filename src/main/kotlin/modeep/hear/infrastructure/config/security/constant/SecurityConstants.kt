package modeep.hear.infrastructure.config.security.constant

object SecurityConstants {
    val PERMIT_PATHS = listOf(
        // Swagger & Static Resources
        "/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html/**",
        "/favicon.ico/**",

        // Auth Public Endpoints
        "/api/v1/auth/login/**",
        "/api/v1/auth/reissue/**",
        "/api/v1/auth/register/**",
        "/api/v1/auth/email/**",
        "/api/v1/auth/email-tickets/**",
        "/api/v1/auth/password-resets/**",

        // Internal API
        "/internal/v1/**",

        // Actuator
        "/actuator/health/**",
        "/actuator/info/**"
    )
}
