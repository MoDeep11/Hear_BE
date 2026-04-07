package modeep.hear.global.document.sticker

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.annotation.ApiInternalServerErrorResponse
import modeep.hear.infrastructure.adapter.`in`.sticker.dto.request.CreateStickerRequest
import org.springframework.http.ResponseEntity

@Tag(name = "Sticker", description = "Sticker 도메인 내부 통신용 API")
interface StickerInternalDocument {
    @Operation(
        summary = "스티커 생성 콜백",
        description = "스티커 생성 완료 후 콜백을 위한 API입니다."
    )
    @ApiInternalServerErrorResponse
    fun createSticker(
        request: CreateStickerRequest
    ): ResponseEntity<ApiResult<Unit>>
}
