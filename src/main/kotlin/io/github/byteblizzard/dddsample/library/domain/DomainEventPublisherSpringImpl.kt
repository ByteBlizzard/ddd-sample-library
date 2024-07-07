package io.github.byteblizzard.dddsample.library.domain

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class DomainEventPublisherSpringImpl(
    private val applicationContext: ApplicationContext
): DomainEventPublisher {
    override fun publish(event: DomainEvent) {
        applicationContext.publishEvent(event)
    }
}