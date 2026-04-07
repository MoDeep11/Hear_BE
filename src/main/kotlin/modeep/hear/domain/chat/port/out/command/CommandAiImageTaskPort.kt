package modeep.hear.domain.chat.port.out.command

import modeep.hear.domain.chat.model.AiImageTask
import java.util.UUID

interface CommandAiImageTaskPort {
    fun save(task: AiImageTask)

    fun delete(taskId: UUID)
}
