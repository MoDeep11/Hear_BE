package modeep.hear.infrastructure.config.security

import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain

class FilterConfig : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(http: HttpSecurity) {
        // todo: filter 추가 후 addFilterBefore()로 필터 설정
        http.csrf { it.disable() }
    }
}
