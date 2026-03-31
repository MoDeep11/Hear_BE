package modeep.hear.domain.user.port.`in`

import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdatePasswordRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdatePasswordResponse

interface UpdatePasswordUseCase {
    fun execute(request: UpdatePasswordRequest): UpdatePasswordResponse
}
