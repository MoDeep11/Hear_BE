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
                .map { img -> img.toModel() }
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
            imageModel.toEntity(diaryEntity)
        }

        diaryEntity.updateImages(imageEntities)

        return diaryEntity
    }

    fun DiaryImageJpaEntity.toModel(): DiaryImage {
        return DiaryImage(
            id = this.id,
            diaryId = this.diary?.id,
            imageUrl = this.imageUrl,
            order = this.order,
            sourceType = this.sourceType,
            diaryImageStatus = this.diaryImageStatus,
            sessionId = this.sessionId,
            baseTime = baseTimeMapper.toModel(this.baseTime)
        )
    }

    fun DiaryImage.toEntity(diary: DiaryJpaEntity?): DiaryImageJpaEntity {
        return DiaryImageJpaEntity(
            id = this.id,
            diary = diary,
            imageUrl = this.imageUrl,
            order = this.order,
            sourceType = this.sourceType,
            sessionId = this.sessionId,
            diaryImageStatus = this.diaryImageStatus
        )
    }
}
