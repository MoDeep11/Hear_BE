package modeep.hear.global.error

import modeep.hear.infrastructure.external.openfeign.discord.DiscordSendService
import org.springframework.stereotype.Component

@Component
class ExceptionNotifier(
    private val discordSendService: DiscordSendService
) {
    fun notify(e: Exception, errorCode: ErrorCode, requestUri: String) {
        discordSendService.sendErrorLog(e, errorCode, requestUri)
    }
}
