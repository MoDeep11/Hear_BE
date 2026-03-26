package modeep.hear.infrastructure.adapter.out.diary.persistence

import modeep.hear.domain.diary.model.DiaryImage
import modeep.hear.domain.diary.port.out.DiaryImagePort
import modeep.hear.infrastructure.adapter.out.diary.mapper.DiaryImageMapper
import modeep.hear.infrastructure.adapter.out.diary.persistence.repository.DiaryImageRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class DiaryImagePersistenceAdapter(
    private val repo: DiaryImageRepository,
    private val mapper: DiaryImageMapper
) : DiaryImagePort {

    // --Query--//
    override fun findAllByChatId(chatId: UUID): List<DiaryImage> {
        return repo.findAllByChatId(chatId)
            .map{img ->
                mapper.toModel(
                    entity = img
                )
            }
    }

    // --Command--//
    override fun saveAll(diaryImages: List<DiaryImage>) {
        val savedDiaryImages = diaryImages.map { mapper.toEntity(it) }
        repo.saveAll(savedDiaryImages)
    }

    override fun delete(diaryImageId: UUID) {
        repo.deleteById(diaryImageId)
    }
}
