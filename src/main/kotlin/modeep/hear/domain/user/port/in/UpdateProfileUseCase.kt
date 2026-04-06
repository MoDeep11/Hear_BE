package modeep.hear.domain.user.port.`in`

import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateProfileRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdateProfileResponse

interface UpdateProfileUseCase {
    fun execute(request: UpdateProfileRequest): UpdateProfileResponse
}
