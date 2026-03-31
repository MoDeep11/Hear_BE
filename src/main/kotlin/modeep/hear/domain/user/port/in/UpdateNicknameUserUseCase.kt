package modeep.hear.domain.user.port.`in`

import modeep.hear.infrastructure.adapter.`in`.user.dto.request.UpdateNicknameRequest
import modeep.hear.infrastructure.adapter.`in`.user.dto.response.UpdateNicknameResponse

interface UpdateNicknameUserUseCase {
    fun execute(request: UpdateNicknameRequest): UpdateNicknameResponse
}
