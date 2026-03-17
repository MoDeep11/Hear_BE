package modeep.hear.domain.auth.port.`in`

import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.RegisterRequest
import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.TokenResponse

interface RegisterAuthUseCase {
    fun execute(request: RegisterRequest): TokenResponse
}
