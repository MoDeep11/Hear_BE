package modeep.hear.infrastructure.external.openfeign.discord

import modeep.hear.infrastructure.config.openfeign.OpenFeignConfig
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "discord-webhook",
    url = "\${api.discord.webhook.url}",
    configuration = [OpenFeignConfig::class],
)
interface DiscordWebhookClient {
    @PostMapping
    fun sendWebhook(
        @RequestBody request: DiscordWebhookRequest,
    )
}
