package modeep.hear.domain.auth.port.`in`

import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.RegisterRequest

interface RegisterAuthUseCase {
    fun execute(request: RegisterRequest)
}
