package modeep.hear.domain.diary.port.`in`

import modeep.hear.domain.diary.model.Diary

interface QueryDiariesUseCase {
    fun execute(
        imageType: String,
        hasPhoto: Boolean,
        yearMonth: String,
        limit: Int,
        sort: String,
        tag: String?
    ): List<Diary>
}
// `imageType` : MANUAL(default) / AI_MADE
//
// `hasPhoto` : ture(default) / false
//
// `yearMonth` : yyyy-mm, (default) 현재 연-월
//
// `limit`: (default) Int.MAX_VALUE
//
// `sort`: (default) createdAt,desc
//
// `tag`: (default) null, 태그 검색용
