package modeep.hear.infrastructure.adapter.out.diary.mapper

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.diary.entity.DiaryImageJpaEntity
import modeep.hear.infrastructure.adapter.out.diary.entity.DiaryJpaEntity
import org.springframework.stereotype.Component

@Component
class DiaryMapperImpl(
    private val baseTimeMapper: BaseTimeMapper
) : DiaryMapper {
    override fun toModel(entity: DiaryJpaEntity): Diary {
        return Diary(
            id = entity.id,
            userId = entity.userId,
            content = entity.content,
            emotion = entity.emotion,
            tags = entity.tags,
            baseTime = baseTimeMapper.toModel(entity.baseTime), // JpaBaseTime -> BaseTime
            sourceType = entity.sourceType,
            sessionId = entity.sessionId,
            diaryImages = entity.diaryImages
                .map { toImageModel(it) }
                .toMutableList()
        )
    }

    override fun toEntity(model: Diary): DiaryJpaEntity {
        val diaryEntity = DiaryJpaEntity(
            userId = model.userId ?: throw BusinessException(UserErrorCode.USER_NOT_FOUND),
            content = model.content,
            emotion = model.emotion,
            tags = model.tags,
            sourceType = model.sourceType,
            sessionId = model.sessionId,
            id = model.id
        )

        val imageEntities = model.diaryImages.map { imageModel ->
            val imageEntity = toImageEntity(imageModel)

            imageEntity.assignDiary(diaryEntity)

            imageEntity
        }.toMutableList()

        diaryEntity.updateImages(imageEntities)

        return diaryEntity
    }

    override fun toImageModel(entity: DiaryImageJpaEntity): DiaryImage {
        return DiaryImage(
            id = entity.id,
            diaryId = entity.diary?.id,
            imageUrl = entity.imageUrl,
            order = entity.order,
            sourceType = entity.sourceType,
            diaryImageStatus = entity.diaryImageStatus,
            baseTime = baseTimeMapper.toModel(entity.baseTime)
        )
    }

    override fun toImageEntity(model: DiaryImage): DiaryImageJpaEntity {
        return DiaryImageJpaEntity(
            id = model.id,
            imageUrl = model.imageUrl,
            order = model.order,
            sourceType = model.sourceType,
            diaryImageStatus = model.diaryImageStatus
        )
    }
}