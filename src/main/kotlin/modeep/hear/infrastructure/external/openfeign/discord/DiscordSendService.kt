package modeep.hear.infrastructure.external.openfeign.discord

import io.github.oshai.kotlinlogging.KotlinLogging
import modeep.hear.global.error.ErrorCode
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class DiscordSendService(
    private val discordWebhookClient: DiscordWebhookClient
) {
    @Async("discordAsyncExecutor")
    fun sendErrorLog(
        e: Exception,
        errorCode: ErrorCode,
        requestUri: String
    ) {
        val request = DiscordWebhookRequest.createErrorEmbed(e, errorCode, requestUri)
        runCatching {
            discordWebhookClient.sendWebhook(request)
        }.onFailure {
            log.warn(it) { "Failed to send error log to Discord" }
        }
    }

    @Async("discordAsyncExecutor")
    fun sendCustomMessage(request: DiscordWebhookRequest) {
        discordWebhookClient.sendWebhook(request)
    }
}
