package modeep.hear.domain.user.port.`in`

import modeep.hear.infrastructure.adapter.`in`.user.dto.response.GetRandomProfileImageResponse

interface GetRandomProfileImageUseCase {
    fun execute(): GetRandomProfileImageResponse
}
