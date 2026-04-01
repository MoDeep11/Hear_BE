package modeep.hear.domain.user.vo

enum class DefaultProfileImageUrl(
    val value: String
) {
    HAPPY("link"), // todo: 링크 추가
    SAD("link"),
    ANGRY("link"),
    ANXIETY("link"),
    NEUTRAL("link"),
    NORMAL("https://hear-official.s3.ap-northeast-2.amazonaws.com/default/user-profile/hear_normal_dust.svg");

    companion object {
        fun random(): DefaultProfileImageUrl {
            return entries.random()
        }
    }
}
