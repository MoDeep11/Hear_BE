package modeep.hear.infrastructure.external.openfeign.discord

data class DiscordWebhookRequest(
    val embeds: List<DiscordEmbed>,
) {
    companion object {
        private const val ERROR_COLOR = 15158332
        private const val MONITORING_FOOTER = "Hear Error Monitoring"

        fun createErrorEmbed(
            e: Exception,
            requestUri: String,
        ): DiscordWebhookRequest {
            val safeUri = maskSensitiveInfo(requestUri)
            val exceptionName = e.javaClass.simpleName
            val errorMessage = e.message ?: "Unknown error"

            return DiscordWebhookRequest(
                embeds =
                    listOf(
                        DiscordEmbed(
                            title = exceptionName,
                            description = errorMessage,
                            color = ERROR_COLOR,
                            fields =
                                listOf(
                                    EmbedField(name = "Request URI", value = "`$safeUri`", inline = true),
                                    EmbedField(name = "Exception", value = "`$exceptionName`", inline = true),
                                    // todo: 커스텀 error 정의 후, error status code 등 다른 필드 추가 필요 + 메시지 통으로 보내지 않도록
                                    EmbedField(name = "Message", value = errorMessage.take(1024), inline = false),
                                ),
                            footer = EmbedFooter(text = MONITORING_FOOTER),
                        ),
                    ),
            )
        }

        private fun maskSensitiveInfo(uri: String): String {
            val sensitiveKeys = listOf("ServiceKey", "accessToken", "auth", "token")

            var maskedUri = uri
            sensitiveKeys.forEach { key ->
                val regex = Regex("($key=)[^&]*")
                maskedUri = maskedUri.replace(regex, "$1********")
            }
            return maskedUri
        }
    }
}

data class DiscordEmbed(
    val title: String,
    val description: String,
    val color: Int,
    val fields: List<EmbedField>,
    val timestamp: String =
        java.time.OffsetDateTime
            .now()
            .toString(),
    val footer: EmbedFooter? = null,
)

data class EmbedField(
    val name: String,
    val value: String,
    val inline: Boolean = false,
)

data class EmbedFooter(
    val text: String,
)
