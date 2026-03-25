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

    //--Query--//
    override fun findBySessionId(sessionId: UUID): DiaryImage? {
        return repo.findBySessionId(sessionId)?.let { mapper.toModel(it) }
    }

    //--Command--//
    override fun delete(diaryImageId: UUID) {
        repo.deleteById(diaryImageId)
    }
}