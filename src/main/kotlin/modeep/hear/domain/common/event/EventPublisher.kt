package modeep.hear.domain.common.event

interface EventPublisher {
    fun publish(event: Any)
}