package io.github.byteblizzard.dddsample.library.domain.availablebooks

import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.book.BookPutOnShelfEvent
import io.github.byteblizzard.dddsample.library.domain.lend.BookReturnedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class AddToAvailableListener(
    private val addToAvailablePolicy: AddToAvailablePolicy
) {
    @EventListener(classes = [BookReturnedEvent::class, BookPutOnShelfEvent::class])
    fun onEvent(event: DomainEvent) {
        addToAvailablePolicy.onDomainEvent(event)
    }
}