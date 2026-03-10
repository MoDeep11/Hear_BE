package modeep.hear.infrastructure.external.openfeign.discord

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class DiscordSendService(
    private val discordWebhookClient: DiscordWebhookClient,
) {
    @Async("discordAsyncExecutor")
    fun sendErrorLog(
        e: Exception,
        requestUri: String,
    ) {
        val request = DiscordWebhookRequest.createErrorEmbed(e, requestUri)
        discordWebhookClient.sendWebhook(request)
    }

    @Async("discordAsyncExecutor")
    fun sendCustomMessage(request: DiscordWebhookRequest) {
        discordWebhookClient.sendWebhook(request)
    }
}
