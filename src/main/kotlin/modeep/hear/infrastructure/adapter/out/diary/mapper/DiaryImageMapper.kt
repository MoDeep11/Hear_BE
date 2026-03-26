package modeep.hear.infrastructure.adapter.out.diary.mapper

import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.diary.entity.DiaryImageJpaEntity
import modeep.hear.infrastructure.adapter.out.diary.entity.DiaryJpaEntity
import org.springframework.stereotype.Component

@Component
class DiaryImageMapper(
    private val baseTimeMapper: BaseTimeMapper
) {
    fun toModel(entity: DiaryImageJpaEntity): DiaryImage {
        return DiaryImage(
            id = entity.id,
            diaryId = entity.diary?.id,
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
