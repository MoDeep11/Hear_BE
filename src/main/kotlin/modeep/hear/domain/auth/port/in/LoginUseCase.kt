package modeep.hear.domain.auth.port.`in`

import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LoginRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse

interface LoginUseCase {
    fun execute(request: LoginRequest): TokenResponse
}
