package io.github.byteblizzard.dddsample.library.domain.availablebooks

import io.github.byteblizzard.dddsample.library.domain.book.BookRepository
import io.github.byteblizzard.dddsample.library.domain.book.BookTakenOffShelfEvent
import io.github.byteblizzard.dddsample.library.domain.lend.BookLentOutEvent
import io.github.byteblizzard.dddsample.library.domain.reservation.BookReservedEvent
import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.DomainEventListener
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class RemoveAvailableBookPolicy(
    private val bookRepository: BookRepository,
    private val removeFromAvailableCmdHandler: RemoveFromAvailableCmdHandler
) : DomainEventListener {

    @EventListener(BookTakenOffShelfEvent::class)
    override fun onDomainEvent(event: DomainEvent) {
        when (event) {
            is BookReservedEvent -> removeFromAvailableBooks(event.bookId)
            is BookTakenOffShelfEvent -> removeFromAvailableBooks(event.bookId)
            is BookLentOutEvent -> removeFromAvailableBooks(event.bookId)
        }
    }

    private fun removeFromAvailableBooks(bookId: String) {
        val book = bookRepository.findByIdOrError(bookId)
        removeFromAvailableCmdHandler.handle(RemoveFromAvailableCmd(
            isbn = book.isbn,
            bookId= bookId
        ))
    }
}