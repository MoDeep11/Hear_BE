package modeep.hear.infrastructure.adapter.`in`.s3.dto.request

import jakarta.validation.constraints.Min
import modeep.hear.domain.storage.vo.ImageAction
import java.util.UUID

data class UploadDiaryImageRequest(
    val action: ImageAction,
    val imageUrl: String?,
    val id: UUID? = null,

    @field:Min(value = 0)
    val order: Int,
    val isDeleted: Boolean = false
) {
    init {
        when (action) {
            ImageAction.ADD -> {
                // 1. 추가 시에는 이미지가 반드시 있어야 함
                require(!imageUrl.isNullOrBlank()) { "이미지 추가 시 imageUrl은 필수입니다." }
                // 2. 추가 시에는 기존 ID가 있으면 안 됨
                require(id == null) { "새 이미지 추가 시 id를 지정할 수 없습니다." }
                // 3. 추가 시 삭제 상태일 수 없음
                require(!isDeleted) { "추가 액션과 삭제 상태(isDeleted=true)는 공존할 수 없습니다." }
            }

            ImageAction.DELETE -> {
                // 1. 삭제 시에는 어떤 대상을 지울지 ID가 필수임
                requireNotNull(id) { "이미지 삭제 시 대상 id는 필수입니다." }
                // 2. 논리적 정합성: 삭제 액션이면 isDeleted도 true여야 함
                require(isDeleted) { "삭제 액션 시 isDeleted 필드는 true여야 합니다." }
            }

            ImageAction.UPDATE_ORDER -> {
                // 1. 순서 변경 시에도 대상 ID는 필수
                requireNotNull(id) { "순서 변경 시 대상 id는 필수입니다." }
                // 2. 순서 변경 시에는 새로운 이미지를 올릴 수 없음
                require(imageUrl == null) { "순서 변경 시에는 imageUrl을 수정할 수 없습니다." }
                // 3. 순서 변경 시 삭제 상태일 수 없음
                require(!isDeleted) { "순서 변경 액션과 삭제 상태는 공존할 수 없습니다." }
            }
        }
    }
}
