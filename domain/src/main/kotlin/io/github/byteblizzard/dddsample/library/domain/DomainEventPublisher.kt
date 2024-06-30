package io.github.byteblizzard.dddsample.library.domain

interface DomainEventPublisher {
    fun publish(event: DomainEvent)
}