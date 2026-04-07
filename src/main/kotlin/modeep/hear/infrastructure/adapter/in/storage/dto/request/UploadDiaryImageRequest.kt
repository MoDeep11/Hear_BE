package modeep.hear.infrastructure.adapter.`in`.storage.dto.request

import com.fasterxml.jackson.annotation.JsonAlias
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.Min
import modeep.hear.domain.storage.vo.ImageAction
import java.util.UUID

data class UploadDiaryImageRequest(
    val action: ImageAction,
    @field:JsonAlias("image_url")
    val imageUrl: String?,
    val id: UUID? = null,

    @field:Min(value = 0)
    val order: Int,
    @field:JsonAlias("is_deleted")
    val isDeleted: Boolean = false
) {
    @AssertTrue(message = "사진 업로드의 데이터 정합성이 맞지 않습니다.")
    fun isValid(): Boolean {
        return when (action) {
            ImageAction.ADD -> !imageUrl.isNullOrBlank() && id == null && !isDeleted
            ImageAction.DELETE -> id != null && isDeleted
            ImageAction.UPDATE_ORDER -> id != null && imageUrl == null && !isDeleted
        }
    }
}
