package modeep.hear.infrastructure.adapter.out.chat.mapper

import modeep.hear.domain.chat.model.AiImageTask
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.chat.entity.AiImageTaskJpaEntity
import org.springframework.stereotype.Component

@Component
class AiImageTaskMapper(
    private val baseTimeMapper: BaseTimeMapper
) {
    fun toModel(entity: AiImageTaskJpaEntity) : AiImageTask {
        return AiImageTask(
            id = entity.id,
            sessionId = entity.sessionId,
            diaryId = entity.diaryId,
            status = entity.status,
            baseTime = baseTimeMapper.toModel(entity.baseTime)
        )
    }

    fun toEntity(model: AiImageTask) : AiImageTaskJpaEntity {
        return AiImageTaskJpaEntity(
            sessionId = model.sessionId,
            diaryId = model.diaryId,
            status = model.status,
            id = model.id
        )
    }
}