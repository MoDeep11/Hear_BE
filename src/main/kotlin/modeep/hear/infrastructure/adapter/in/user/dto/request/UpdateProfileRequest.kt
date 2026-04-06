package modeep.hear.infrastructure.adapter.`in`.user.dto.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateProfileRequest(
    @field:JsonAlias("profile_image_url")
    val profileImageUrl: String,

    @field:NotBlank
    @field:Size(min = 2, max = 20)
    val nickname: String
)
