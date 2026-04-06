package modeep.hear.infrastructure.adapter.`in`.user.dto.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.NotBlank

data class UpdateProfileRequest(
    @field:JsonAlias("profile_image_url")
    val profileImageUrl: String,

    @field:NotBlank
    val nickname: String
)
