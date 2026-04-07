package modeep.hear.domain.sticker.service

import modeep.hear.domain.auth.port.out.SecurityPort
import modeep.hear.domain.sticker.port.`in`.GetStickersUseCase
import modeep.hear.domain.sticker.port.out.StickerPort
import modeep.hear.infrastructure.adapter.`in`.sticker.dto.response.GetStickersResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetStickersService(
    private val stickerPort: StickerPort,
    private val securityPort: SecurityPort
) : GetStickersUseCase {
    override fun execute(): List<GetStickersResponse> {
        val user = securityPort.getCurrentUser()
        val stickers = stickerPort.findAllByUserId(user.id)

        return stickers.map { sticker ->
            GetStickersResponse(
                stickerId = sticker.id,
                imageUrl = sticker.imageUrl,
                keyword = sticker.keyword
            )
        }
    }
}
