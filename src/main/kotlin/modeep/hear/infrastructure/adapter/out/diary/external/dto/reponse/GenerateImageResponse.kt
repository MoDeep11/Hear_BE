package modeep.hear.infrastructure.adapter.out.diary.external.dto.reponse

import com.fasterxml.jackson.annotation.JsonAlias
import java.util.UUID

data class GenerateImageResponse(
    @field:JsonAlias("task_id")
    val taskId: UUID
)
