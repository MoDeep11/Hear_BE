package modeep.hear.infrastructure.adapter.out.diary.external.dto.reponse

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import modeep.hear.domain.common.vo.Emotion
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo

data class GenerateDiaryResponse(
    val userInfo: UserInfo,
    @field:NotBlank
    val content: String,
    val emotion: Emotion,
    @field:NotEmpty
    val tags: List<@NotBlank String>
)
