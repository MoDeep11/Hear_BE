package modeep.hear.infrastructure.adapter.`in`.auth.dto.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotBlank

data class ReissueRequest(
    @field:JsonAlias("refresh_token")
    @field:NotBlank
    val refreshToken: String
)
