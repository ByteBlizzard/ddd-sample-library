package io.github.byteblizzard.dddsample.library.domain

interface DomainEventListener{
    fun onDomainEvent(event: DomainEvent)
}