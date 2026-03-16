package modeep.hear.domain.auth.port.`in`

import modeep.hear.infrastructure.adapter.`in`.auth.dto.SendEmailRequest

interface SendEmailUseCase {
    fun execute(request: SendEmailRequest)
}
