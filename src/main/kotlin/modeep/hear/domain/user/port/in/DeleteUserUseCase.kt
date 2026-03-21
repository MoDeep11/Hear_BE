package modeep.hear.domain.user.port.`in`

import modeep.hear.infrastructure.adapter.`in`.user.dto.request.DeleteUserRequest

interface DeleteUserUseCase {
    fun execute(
        request: DeleteUserRequest,
        accessToken: String
    )
}
