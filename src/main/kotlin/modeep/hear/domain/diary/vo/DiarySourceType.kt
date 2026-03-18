package modeep.hear.domain.diary.vo

enum class DiarySourceType(
    val label: String
) {
    MANUAL("유저 생성"),
    AI_MADE("AI 생성");

    companion object {
        const val DEFAULT_TYPE = "AI_MADE"
    }
}
