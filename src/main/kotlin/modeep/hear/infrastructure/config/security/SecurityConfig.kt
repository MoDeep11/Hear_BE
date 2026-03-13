package modeep.hear.infrastructure.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Profile("!test")
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val corsConfig: CorsConfig
) {
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
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
                    .requestMatchers(HttpMethod.OPTIONS, "**").permitAll()
                    // auth
                    .requestMatchers(HttpMethod.GET, "/auth/**").permitAll()
            }
            .with(FilterConfig(), Customizer.withDefaults())
            .build()

        return http.build()
    }
}
