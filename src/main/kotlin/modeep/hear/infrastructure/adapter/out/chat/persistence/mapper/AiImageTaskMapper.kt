package modeep.hear.infrastructure.adapter.out.chat.persistence.mapper

import modeep.hear.domain.chat.model.AiImageTask
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.chat.persistence.entity.AiImageTaskJpaEntity
import org.springframework.stereotype.Component

@Component
class AiImageTaskMapper(
    private val baseTimeMapper: BaseTimeMapper
) {
    fun toModel(entity: AiImageTaskJpaEntity): AiImageTask {
        return AiImageTask(
            id = entity.id,
            chatId = entity.chatId,
            diaryId = entity.diaryId,
            status = entity.status,
            baseTime = baseTimeMapper.toModel(entity.baseTime)
        )
    }

    fun toEntity(model: AiImageTask, isNew: Boolean): AiImageTaskJpaEntity {
        val entity = AiImageTaskJpaEntity(
            chatId = model.chatId,
            diaryId = model.diaryId,
            status = model.status,
            id = model.id
        )
        if (!isNew) {
            entity.markNotNew()
        }
        return entity
    }
}
