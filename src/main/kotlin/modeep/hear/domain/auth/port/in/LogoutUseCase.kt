package modeep.hear.domain.auth.port.`in`

import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.LogoutRequest

interface LogoutUseCase {
    fun execute(request: LogoutRequest, accessToken: String)
}