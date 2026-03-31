package modeep.hear.domain.user.port.`in`

import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UserProfileResponse

interface GetUserProfileUseCase {
    fun execute(): UserProfileResponse
}
