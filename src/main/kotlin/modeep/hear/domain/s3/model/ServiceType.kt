package modeep.hear.domain.s3.model

enum class ServiceType(
    val folder: String
) {
    PROFILE("user-profile"),
    DIARY("diary"),
    STICKER("sticker"),
    CHAT("chat"),
    AI_IMAGE("ai-image"),
    AI_AUDIO("ai-audio"),
    AI_VIDEO("ai-video"),
    AI_TEXT("ai-text"),
    AI_DOCUMENT("ai-document"),
    AI_CHAT("ai-chat"),
    AI_CHAT_HISTORY("ai-chat-history")
}
