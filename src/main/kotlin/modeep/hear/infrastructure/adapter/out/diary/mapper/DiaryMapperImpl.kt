package modeep.hear.infrastructure.adapter.out.diary.mapper

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.user.exception.UserErrorCode
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.diary.entity.DiaryJpaEntity
import org.springframework.stereotype.Component

@Component
class DiaryMapperImpl(
    private val baseTimeMapper: BaseTimeMapper,
    private val diaryImageMapper: DiaryImageMapper
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
            chatId = entity.chatId,
            diaryImages = entity.diaryImages
                .map { diaryImageMapper.toModel(entity.id, it) }
                .toMutableList()
        )
    }

    override fun toEntity(model: Diary): DiaryJpaEntity {
        val diaryEntity = DiaryJpaEntity(
            userId = model.userId,
            content = model.content,
            emotion = model.emotion,
            tags = model.tags,
            sourceType = model.sourceType,
            chatId = model.chatId,
            id = model.id
        )

        val imageEntities = model.diaryImages.map {
            diaryImageMapper.toEntity(it, diaryEntity)
        }

        diaryEntity.updateImages(imageEntities)

        return diaryEntity
    }
}
