package modeep.hear.infrastructure.adapter.`in`.sticker

import modeep.hear.domain.sticker.port.`in`.GetStickersUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.sticker.StickerApiDocument
import modeep.hear.infrastructure.adapter.`in`.sticker.dto.response.GetStickersResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/stickers")
class StickerWebAdapter(
    private val getStickersUseCase: GetStickersUseCase
) : StickerApiDocument {
    @GetMapping
    override fun getStickers(): ResponseEntity<ApiResult<List<GetStickersResponse>>> {
        return ResponseEntity.ok(ApiResult(data = getStickersUseCase.execute()))
    }
}
