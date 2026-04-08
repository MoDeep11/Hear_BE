package modeep.hear.infrastructure.adapter.`in`.sticker

import modeep.hear.domain.sticker.port.`in`.CreateStickerUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.sticker.StickerInternalDocument
import modeep.hear.infrastructure.adapter.`in`.sticker.dto.request.CreateStickerRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal/v1/stickers")
class StickerInternalAdapter(
    private val createStickerUseCase: CreateStickerUseCase
) : StickerInternalDocument {
    @PatchMapping
    override fun createSticker(request: CreateStickerRequest): ResponseEntity<ApiResult<Unit>> {
        createStickerUseCase.execute(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResult(
                status = 201,
                message = "Sticker created"
            )
        )
    }
}
