package modeep.hear.domain.auth.port.`in`

import modeep.hear.infrastructure.adapter.`in`.auth.dto.VerifyEmailRequest

interface VerifyEmailAuthUseCase {
    fun execute(request: VerifyEmailRequest)
}
