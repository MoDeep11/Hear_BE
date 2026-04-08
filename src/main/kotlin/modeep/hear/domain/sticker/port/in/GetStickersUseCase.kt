package modeep.hear.domain.sticker.port.`in`

import modeep.hear.infrastructure.adapter.`in`.sticker.dto.response.GetStickersResponse

interface GetStickersUseCase {
    fun execute(): List<GetStickersResponse>
}
