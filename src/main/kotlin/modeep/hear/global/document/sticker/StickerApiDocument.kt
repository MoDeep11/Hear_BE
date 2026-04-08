package modeep.hear.global.document.sticker

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.annotation.ApiInternalServerErrorResponse
import modeep.hear.infrastructure.adapter.`in`.sticker.dto.response.GetStickersResponse
import org.springframework.http.ResponseEntity

@Tag(name = "Sticker", description = "Sticker 도메인 관련 API")
interface StickerApiDocument {
    @Operation(
        summary = "스티커 전체 조회",
        description = "유저의 스티커를 전체 조회합니다."
    )
    @ApiInternalServerErrorResponse
    fun getStickers(): ResponseEntity<ApiResult<List<GetStickersResponse>>>
}
