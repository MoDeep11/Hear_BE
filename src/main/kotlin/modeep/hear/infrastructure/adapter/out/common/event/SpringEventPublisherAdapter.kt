package modeep.hear.infrastructure.adapter.out.common.event

import modeep.hear.domain.common.event.EventPublisher
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class SpringEventPublisherAdapter(
    private val applicationEventPublisher: ApplicationEventPublisher
) : EventPublisher {
    override fun publish(event: Any) {
        applicationEventPublisher.publishEvent(event)
    }
}
