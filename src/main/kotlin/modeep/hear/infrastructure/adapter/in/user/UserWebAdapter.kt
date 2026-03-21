package modeep.hear.infrastructure.adapter.`in`.user

import modeep.hear.domain.user.port.`in`.DeleteUserUseCase
import modeep.hear.global.common.response.ApiResult
import modeep.hear.global.document.user.UserApiDocument
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/diaries/me")
class UserWebAdapter(
    private val deleteUserUseCase: DeleteUserUseCase
) : UserApiDocument {

    @DeleteMapping
    override fun deleteUser(): ResponseEntity<ApiResult<Unit>> {
        deleteUserUseCase.execute()
        return ResponseEntity.ok(ApiResult())
    }
}
