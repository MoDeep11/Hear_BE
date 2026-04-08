package modeep.hear.infrastructure.adapter.out.sticker.event

import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.port.out.query.QueryDiaryPort
import modeep.hear.domain.sticker.event.CreateStickerEvent
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.domain.user.port.out.query.QueryUserPort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.sticker.external.StickerExternalAdapter
import modeep.hear.infrastructure.adapter.out.sticker.external.dto.request.GenerateStickerRequest
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class CreateStickerEventAdapter(
    private val stickerExternalAdapter: StickerExternalAdapter,
    private val queryDiaryPort: QueryDiaryPort,
    private val queryUserPort: QueryUserPort,
    private val createStickerTaskComponent: CreateStickerTaskComponent
) {
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    suspend fun execute(event: CreateStickerEvent) {
        val diary = queryDiaryPort.findById(event.diaryId) ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)
        val user = queryUserPort.findById(event.userId) ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND)
        diary.validateOwner(user.id)

        val req = GenerateStickerRequest(
            diaryId = diary.id,
            userId = user.id,
            emotion = diary.emotion,
            content = diary.content
        )
        stickerExternalAdapter.generateSticker(req)
        createStickerTaskComponent.execute(event.diaryId)
    }
}
