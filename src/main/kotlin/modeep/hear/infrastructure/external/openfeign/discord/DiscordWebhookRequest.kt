package modeep.hear.infrastructure.external.openfeign.discord

import modeep.hear.global.error.ErrorCode
import modeep.hear.global.error.exception.BusinessException
import java.time.Instant

data class DiscordWebhookRequest(
    val embeds: List<DiscordEmbed>,
) {
    companion object {
        private const val ERROR_COLOR = 15158332
        private const val MONITORING_FOOTER = "Hear Error Monitoring"

        fun createErrorEmbed(
            e: Exception,
            errorCode: ErrorCode,
            requestUri: String,
        ): DiscordWebhookRequest {
            val safeUri = maskSensitiveInfo(requestUri)
            val exceptionName = e::class.simpleName ?: "UnknownException"

            return DiscordWebhookRequest(
                embeds =
                    listOf(
                        DiscordEmbed(
                            title = errorCode.name,
                            description = errorCode.message,
                            color = ERROR_COLOR,
                            fields =
                                listOf(
                                    EmbedField(name = "Request URI", value = "`$safeUri`", inline = true),
                                    EmbedField(name = "Exception", value = "`$exceptionName`", inline = true),
                                    EmbedField(
                                        name = "Detail Reason",
                                        value = if (e is BusinessException) "```${e.details ?: "N/A"}```" else "```None```",
                                        inline = false
                                    ),
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
    val timestamp: String = Instant.now().toString(),
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
