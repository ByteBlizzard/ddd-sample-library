package io.github.byteblizzard.dddsample.library.domain.availablebooks

import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.DomainEventListener
import io.github.byteblizzard.dddsample.library.domain.book.BookRepository
import io.github.byteblizzard.dddsample.library.domain.lend.BookReturnedEvent
import org.springframework.stereotype.Component

@Component
class AddToAvailablePolicy(
    private val addToAvailableCmdHandler: AddToAvailableCmdHandler,
    private val bookRepository: BookRepository,
): DomainEventListener {
    override fun onDomainEvent(event: DomainEvent) {
        if (event !is BookReturnedEvent) {
            return
        }

        this.addToAvailableCmdHandler.handle(AddToAvailableCmd(
            bookId = event.bookId,
            isbn = bookRepository.findByIdOrError(event.bookId).isbn
        ))

    }

}