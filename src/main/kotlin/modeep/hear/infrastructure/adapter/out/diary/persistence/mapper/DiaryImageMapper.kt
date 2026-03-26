package modeep.hear.infrastructure.adapter.out.diary.persistence.mapper

import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.diary.persistence.entity.DiaryImageJpaEntity
import modeep.hear.infrastructure.adapter.out.diary.persistence.entity.DiaryJpaEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DiaryImageMapper(
    private val baseTimeMapper: BaseTimeMapper
) {
    fun toModel(diaryId: UUID? = null, entity: DiaryImageJpaEntity): DiaryImage {
        return DiaryImage(
            id = entity.id,
            diaryId = diaryId,
            imageUrl = entity.imageUrl,
            order = entity.order,
            sourceType = entity.sourceType,
            diaryImageStatus = entity.diaryImageStatus,
            chatId = entity.chatId,
            baseTime = baseTimeMapper.toModel(entity.baseTime)
        )
    }

    fun toEntity(model: DiaryImage, diary: DiaryJpaEntity? = null): DiaryImageJpaEntity {
        return DiaryImageJpaEntity(
            id = model.id,
            diary = diary,
            imageUrl = model.imageUrl,
            order = model.order,
            sourceType = model.sourceType,
            chatId = model.chatId,
            diaryImageStatus = model.diaryImageStatus
        )
    }
}
