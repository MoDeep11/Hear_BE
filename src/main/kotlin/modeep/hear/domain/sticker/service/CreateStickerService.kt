package modeep.hear.domain.sticker.service

import modeep.hear.domain.sticker.model.Sticker
import modeep.hear.domain.sticker.port.`in`.CreateStickerUseCase
import modeep.hear.domain.sticker.port.out.StickerPort
import modeep.hear.domain.sticker.vo.StickerStatus
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.out.query.QueryUserPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.`in`.sticker.dto.request.CreateStickerRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateStickerService(
    private val stickerPort: StickerPort,
    private val queryUserPort: QueryUserPort
) : CreateStickerUseCase {
    override fun execute(req: CreateStickerRequest) {
        if (!queryUserPort.existsById(req.userId)) throw BusinessException(UserErrorCode.USER_NOT_FOUND)

        val sticker = Sticker.create(
            userId = req.userId,
            status = StickerStatus.COMPLETED,
            imageUrl = req.imageUrl
        )

        stickerPort.save(sticker)
    }
}
