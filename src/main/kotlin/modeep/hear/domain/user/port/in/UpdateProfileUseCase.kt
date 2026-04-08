package modeep.hear.domain.user.port.`in`

import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateProfileRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdateProfileResponse
import org.springframework.web.multipart.MultipartFile

interface UpdateProfileUseCase {
    fun execute(
        request: UpdateProfileRequest,
        image: MultipartFile?
    ): UpdateProfileResponse
}
