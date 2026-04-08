package modeep.hear.domain.sticker.port.`in`

import modeep.hear.infrastructure.adapter.`in`.sticker.dto.request.CreateStickerRequest

interface CreateStickerUseCase {
    fun execute(req: CreateStickerRequest)
}
