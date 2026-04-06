package modeep.hear.domain.user.vo

enum class DefaultProfileImageUrl(
    val value: String
) {
    CLOVER("https://hear-official.s3.ap-northeast-2.amazonaws.com/default/user-profile/hear_clover.svg"),
    TEAR("https://hear-official.s3.ap-northeast-2.amazonaws.com/default/user-profile/hear_tear.svg"),
    SPIKY("https://hear-official.s3.ap-northeast-2.amazonaws.com/default/user-profile/hear_spiky.svg"),
    FLOWER("https://hear-official.s3.ap-northeast-2.amazonaws.com/default/user-profile/hear_flower.svg"),
    STAR("https://hear-official.s3.ap-northeast-2.amazonaws.com/default/user-profile/hear_star.svg"),
    DUST("https://hear-official.s3.ap-northeast-2.amazonaws.com/default/user-profile/hear_dust.svg");

    companion object {
        fun random(): String {
            return entries.random().value
        }
    }
}
