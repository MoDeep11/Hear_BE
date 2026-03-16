package modeep.hear.domain.auth.port.`in`

import modeep.hear.infrastructure.adapter.`in`.auth.dto.CreateUserRequest

interface RegisterAuthUseCase {
    fun execute(request: CreateUserRequest)
}
