package io.github.byteblizzard.dddsample.library.domain

import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.EventQueue
import java.util.LinkedList

class TestEventQueue: EventQueue {
    val list = LinkedList<DomainEvent>()
    override fun enqueue(event: DomainEvent) {
        list.add(event)
    }

    override fun queue(): List<DomainEvent> {
        return list
    }
}