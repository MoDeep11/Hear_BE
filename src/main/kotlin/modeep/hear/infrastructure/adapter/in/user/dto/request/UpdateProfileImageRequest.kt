package modeep.hear.infrastructure.adapter.`in`.user.dto.request

import com.fasterxml.jackson.annotation.JsonAlias

data class UpdateProfileImageRequest(
    @field:JsonAlias("profile_image_url")
    val profileImageUrl: String
)
