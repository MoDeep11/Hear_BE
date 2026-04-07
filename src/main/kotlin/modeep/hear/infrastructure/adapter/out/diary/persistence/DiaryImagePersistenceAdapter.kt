package modeep.hear.infrastructure.adapter.out.diary.persistence

import modeep.hear.domain.diary.exception.DiaryErrorCode
import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.domain.diary.port.out.command.CommandDiaryImagePort
import modeep.hear.domain.diary.port.out.query.QueryDiaryImagePort
import modeep.hear.global.error.exception.BusinessException
import modeep.hear.infrastructure.adapter.out.diary.persistence.mapper.DiaryImageMapper
import modeep.hear.infrastructure.adapter.out.diary.persistence.repository.DiaryImageRepository
import modeep.hear.infrastructure.adapter.out.diary.persistence.repository.DiaryRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DiaryImagePersistenceAdapter(
    private val repo: DiaryImageRepository,
    private val mapper: DiaryImageMapper,
    private val diaryRepository: DiaryRepository
) : QueryDiaryImagePort, CommandDiaryImagePort {

    // --Query--//
    override fun findAllByChatId(chatId: UUID): List<DiaryImage> {
        return repo.findAllByChatId(chatId)
            .map { img ->
                mapper.toModel(
                    entity = img
                )
            }
    }

    // --Command--//
    override fun saveAll(diaryImages: List<DiaryImage>) {
        diaryImages.forEach { save(it) }
    }

    override fun save(diaryImage: DiaryImage) {
        val diaryRef = diaryRepository.getReferenceById(diaryImage.diaryId!!) ?: throw BusinessException(DiaryErrorCode.DIARY_NOT_FOUND)
        val isExist = repo.existsById(diaryImage.id)
        val entity = mapper.toEntity(diaryImage, diaryRef, isNew = !isExist)
        repo.save(entity)
    }

    override fun delete(diaryImageId: UUID) {
        repo.deleteIfExists(diaryImageId)
    }
}
