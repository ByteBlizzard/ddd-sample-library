package io.github.byteblizzard.dddsample.library.domain

interface EventQueue {
    fun enqueue(event: DomainEvent)

    fun queue(): List<DomainEvent>
}