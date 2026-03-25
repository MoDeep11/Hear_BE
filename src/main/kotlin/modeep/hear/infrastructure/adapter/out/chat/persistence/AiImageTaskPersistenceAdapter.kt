package modeep.hear.infrastructure.adapter.out.chat.persistence

import modeep.hear.domain.chat.model.AiImageTask
import modeep.hear.domain.chat.port.out.AiImageTaskPort
import modeep.hear.infrastructure.adapter.out.chat.mapper.AiImageTaskMapper
import modeep.hear.infrastructure.adapter.out.chat.persistence.repository.AiImageTaskRepository
import org.springframework.stereotype.Component

@Component
class AiImageTaskPersistenceAdapter(
    private val mapper: AiImageTaskMapper,
    private val repo: AiImageTaskRepository
) : AiImageTaskPort {

    // --Command--//
    override fun save(task: AiImageTask) {
        repo.save(mapper.toEntity(task))
    }
}
