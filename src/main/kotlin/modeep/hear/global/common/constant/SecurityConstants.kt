package modeep.hear.global.common.constant

object SecurityConstants {
    val PERMIT_PATHS = listOf(
        // Static Resources
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

    val ADMIN_PERMIT_PATHS = listOf(
        "/api/v1/admin/**",

        // Swagger
        "/api-docs/**",
        "/swagger-ui/**",
        "/swagger-ui.html/**"
    )

    val SENSITIVE_FIELDS = listOf(
        "password",
        "oldPassword",
        "newPassword",
        "confirmPassword",
        "account",
        "ssn",
        "credential"
    )

    const val MASKING_TEXT = "********"
}
