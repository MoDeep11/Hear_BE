package modeep.hear.infrastructure.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import modeep.hear.infrastructure.config.security.constant.SecurityConstants
import modeep.hear.infrastructure.security.jwt.JwtAdapter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val corsConfig: CorsConfig,
    private val jwtAdapter: JwtAdapter,
    private val objectMapper: ObjectMapper,
    @param:Qualifier("handlerExceptionResolver")
    private val exceptionResolver: HandlerExceptionResolver
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        return http.csrf { it.disable() }
            .cors { it.configurationSource(corsConfig.corsConfigurationSource()) }
            .formLogin { it.disable() }
            .logout { it.disable() }
            .httpBasic { it.disable() }
            .headers { headers: HeadersConfigurer<HttpSecurity> ->
                headers.frameOptions { frame -> frame.sameOrigin() } // clickjacking 방지
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests { auth ->
                auth
                    // preflight
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .requestMatchers(*SecurityConstants.PERMIT_PATHS.toTypedArray()).permitAll()
                    .anyRequest().authenticated()
            }
            .with(
                FilterConfig(
                    jwtAdapter = jwtAdapter,
                    objectMapper = objectMapper,
                    exceptionResolver = exceptionResolver
                ),
                Customizer.withDefaults()
            )
            .build()
    }
}
