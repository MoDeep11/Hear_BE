package modeep.hear.global.document.user

import io.swagger.v3.oas.annotations.tags.Tag
import modeep.hear.global.common.response.ApiResult
import org.springframework.http.ResponseEntity

@Tag(name = "User", description = "User 도메인 관련 API")
interface UserApiDocument {

    fun deleteUser(): ResponseEntity<ApiResult<Unit>>
}
