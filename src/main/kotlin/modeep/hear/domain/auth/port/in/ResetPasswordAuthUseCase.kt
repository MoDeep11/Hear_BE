package modeep.hear.domain.auth.port.`in`

import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.ResetPasswordRequest

interface ResetPasswordAuthUseCase {
    fun execute(request: ResetPasswordRequest)
}
