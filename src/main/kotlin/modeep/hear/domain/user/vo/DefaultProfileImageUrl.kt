package modeep.hear.domain.user.vo

enum class DefaultProfileImageUrl(
    val value: String
) {
    HAPPY("link"),  // todo: 링크 추가
    SAD("link"),
    ANGRY("link"),
    ANXIETY("link"),
    NEUTRAL("link");

    companion object {
        fun random(): DefaultProfileImageUrl {
            return entries.random()
        }
    }
}
