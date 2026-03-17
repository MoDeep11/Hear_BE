package modeep.hear.domain.auth.port.`in`

import modeep.hear.infrastructure.adapter.`in`.auth.dto.request.VerifyEmailRequest

interface VerifyEmailAuthUseCase {
    fun execute(request: VerifyEmailRequest) : String
}
