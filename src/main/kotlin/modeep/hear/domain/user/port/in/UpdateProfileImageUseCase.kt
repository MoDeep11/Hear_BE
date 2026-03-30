package modeep.hear.domain.user.port.`in`

import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateProfileImageRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdateProfileImageResponse

interface UpdateProfileImageUseCase {
    fun execute(request: UpdateProfileImageRequest) : UpdateProfileImageResponse
}