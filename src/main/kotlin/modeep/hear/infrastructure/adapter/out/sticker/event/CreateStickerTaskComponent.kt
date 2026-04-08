package modeep.hear.infrastructure.adapter.out.sticker.event

import modeep.hear.domain.chat.model.AiImageTask
import modeep.hear.domain.chat.port.out.AiImageTaskPort
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CreateStickerTaskComponent(
    private val taskPort: AiImageTaskPort
) {
    fun execute(diaryId: UUID): AiImageTask {
        val task = AiImageTask.create(
            diaryId = diaryId
        )
        taskPort.save(task)

        return task
    }
}
