package io.github.byteblizzard.dddsample.library.domain

interface DomainEventListener {
    fun onEvent(event: DomainEvent)
}