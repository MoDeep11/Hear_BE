package modeep.hear.global.filter

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ExchangeFilterFunction

@Component
class WebClientLoggingFilter {
    private val log = KotlinLogging.logger {}

    fun loggingFilter(): ExchangeFilterFunction {
        return ExchangeFilterFunction { request, next ->
            val startTime = System.currentTimeMillis()

            next.exchange(request)
                .doOnNext { response ->
                    val duration = System.currentTimeMillis() - startTime
                    log.info { "[WEBCLIENT] ${request.method()} ${request.url()} | Status: ${response.statusCode().value()} | Time: ${duration}ms" }
                }
                .doOnError { error ->
                    log.error { "[WEBCLIENT ERROR] ${request.method()} ${request.url()} | ${error.message}" }
                }
        }
    }
}
