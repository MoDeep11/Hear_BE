package modeep.hear.infrastructure.adapter.out.diary.persistence.mapper

import modeep.hear.domain.diary.model.Diary
import modeep.hear.domain.diary.model.DiaryAiComment
import modeep.hear.global.common.mapper.BaseTimeMapper
import modeep.hear.infrastructure.adapter.out.diary.persistence.entity.DiaryAiCommentJpaEntity
import modeep.hear.infrastructure.adapter.out.diary.persistence.entity.DiaryJpaEntity
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
                .toMutableList(),
            diaryAiComment = entity.diaryAiComment?.let {
                DiaryAiComment(
                    content = it.content,
                    status = it.status,
                    diaryId = it.diaryId,
                    baseTime = baseTimeMapper.toModel(it.baseTime)
                )
            }
        )
    }

    override fun toEntity(model: Diary, isNew: Boolean): DiaryJpaEntity {
        val entity = DiaryJpaEntity(
            userId = model.userId,
            content = model.content,
            emotion = model.emotion,
            tags = model.tags,
            sourceType = model.sourceType,
            chatId = model.chatId,
            id = model.id
        )
        if (!isNew) {
            entity.markNotNew()
        }

        val imageEntities = model.diaryImages.map {
            diaryImageMapper.toEntity(it, entity, true)
        }
        entity.updateImages(imageEntities)

        model.diaryAiComment?.let { comment ->
            entity.diaryAiComment = DiaryAiCommentJpaEntity(
                diaryId = entity.id,
                diary = entity,
                content = comment.content,
                status = comment.status
            )
        }
        return entity
    }
}
