package modeep.hear.domain.chat.port.out.command

import modeep.hear.domain.chat.model.AiImageTask

interface CommandAiImageTaskPort {
    fun save(task: AiImageTask)
}
