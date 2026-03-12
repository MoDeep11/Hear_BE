package modeep.hear.infrastructure.external.openfeign.discord

import modeep.hear.global.error.ErrorCode
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class DiscordSendService(
    private val discordWebhookClient: DiscordWebhookClient,
) {
    @Async("discordAsyncExecutor")
    fun sendErrorLog(
        e: Exception,
        errorCode: ErrorCode,
        requestUri: String,
    ) {
        val request = DiscordWebhookRequest.createErrorEmbed(e, errorCode, requestUri)
        discordWebhookClient.sendWebhook(request)
    }

    @Async("discordAsyncExecutor")
    fun sendCustomMessage(request: DiscordWebhookRequest) {
        discordWebhookClient.sendWebhook(request)
    }
}
