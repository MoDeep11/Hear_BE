package modeep.hear.infrastructure.adapter.out.diary.external.dto.request

import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.History
import modeep.hear.infrastructure.adapter.out.chat.external.dto.vo.UserInfo

data class GenerateDiaryRequest(
    val userInfo: UserInfo,
    val history: List<History>
)
