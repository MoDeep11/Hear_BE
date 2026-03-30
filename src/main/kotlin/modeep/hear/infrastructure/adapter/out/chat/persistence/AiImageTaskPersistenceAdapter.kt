package modeep.hear.infrastructure.adapter.out.chat.persistence

import modeep.hear.domain.chat.model.AiImageTask
import modeep.hear.domain.chat.port.out.AiImageTaskPort
import modeep.hear.infrastructure.adapter.out.chat.persistence.mapper.AiImageTaskMapper
import modeep.hear.infrastructure.adapter.out.chat.persistence.repository.AiImageTaskRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class AiImageTaskPersistenceAdapter(
    private val mapper: AiImageTaskMapper,
    private val repo: AiImageTaskRepository
) : AiImageTaskPort {
    // --Query--//
    override fun findByChatId(chatId: UUID): AiImageTask? {
        return repo.findByChatId(chatId)?.let { mapper.toModel(it) }
    }

    override fun findByDiaryId(diaryId: UUID): AiImageTask? {
        return repo.findByDiaryId(diaryId)?.let { mapper.toModel(it) }
    }

    // --Command--//
    override fun save(task: AiImageTask) {
        repo.save(mapper.toEntity(task))
    }

    override fun delete(task: AiImageTask) {
        repo.delete(mapper.toEntity(task))
    }
}
