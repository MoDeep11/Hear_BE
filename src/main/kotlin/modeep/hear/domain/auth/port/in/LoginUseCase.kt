package modeep.hear.domain.auth.port.`in`

import modeep.hear.infrastructure.adapter.`in`.auth.dto.response.LoginResponse

interface LoginUseCase {
    fun execute(response: LoginResponse): LoginResponse
}