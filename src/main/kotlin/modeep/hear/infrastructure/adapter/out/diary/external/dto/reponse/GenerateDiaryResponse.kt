package modeep.hear.infrastructure.adapter.out.diary.external.dto.reponse

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import modeep.hear.domain.common.vo.Emotion
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo

data class GenerateDiaryResponse(
    @field:JsonAlias("user_info")
    val userInfo: UserInfo,
    @field:NotBlank
    val content: String,
    val emotion: Emotion,
    @field:NotEmpty
    val tags: List<@NotBlank String>
)
