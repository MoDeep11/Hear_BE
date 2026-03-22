package modeep.hear.infrastructure.config.env

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI().info(
            Info()
                .title("Hear API 명세서")
                .description("감정 분석 AI 일기 서비스 Hear 의 백엔드 API 명세서입니다.")
                .version("1.0.0")
        )
    }
}