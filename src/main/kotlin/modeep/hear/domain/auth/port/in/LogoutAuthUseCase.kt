package modeep.hear.domain.auth.port.`in`

import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LogoutRequest

interface LogoutAuthUseCase {
    fun execute(request: LogoutRequest, rawAccessToken: String)
}
