package modeep.hear.infrastructure.config.webflux

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class WebClientConfig(
    private val properties: InternalApiProperties
) {
    @Bean
    fun internalWebClient(builder: WebClient.Builder): WebClient {
        // 타임아웃 설정을 위한 HttpClient 구성
        val httpClient = HttpClient.create()
            .responseTimeout(Duration.ofMillis(properties.timeout))

        return builder
            .baseUrl(properties.baseUrl) // 환경변수에서 가져온 URL 적용
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }
}