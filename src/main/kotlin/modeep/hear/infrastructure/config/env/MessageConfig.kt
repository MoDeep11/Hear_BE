package modeep.hear.infrastructure.config.env

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.validation.Validator
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver
import java.util.Locale

@Configuration
class MessageConfig : WebMvcConfigurer {

    @Bean
    fun messageSource(): MessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasenames("messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    @Bean
    fun localValidatorFactoryBean(): LocalValidatorFactoryBean {
        val bean = LocalValidatorFactoryBean()
        bean.setValidationMessageSource(messageSource())
        return bean
    }

    override fun getValidator(): Validator = localValidatorFactoryBean()

    @Bean
    fun localeResolver(): LocaleResolver {
        val sessionLocaleResolver = AcceptHeaderLocaleResolver()
        sessionLocaleResolver.setDefaultLocale(Locale.KOREA)
        return sessionLocaleResolver
    }
}
