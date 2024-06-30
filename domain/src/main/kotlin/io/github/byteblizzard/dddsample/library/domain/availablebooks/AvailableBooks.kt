package io.github.byteblizzard.dddsample.library.domain.availablebooks

import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.DomainRegistry.eventPublisher

interface AvailableBooks {
    fun containsBook(bookId: String): Boolean
    fun add(bookId: String)
    fun remove(bookId: String)
    fun isEmpty(): Boolean
    fun chooseBookRandomly(): String
}

class AvailableBooksImpl(
    val isbn: String,
    val bookIds: MutableSet<String>
): AvailableBooks {
    override fun containsBook(bookId: String): Boolean {
        return this.bookIds.contains(bookId)
    }

    override fun add(bookId: String) {
        if (this.bookIds.contains(bookId)) {
            return
        }
        this.bookIds.add(bookId)
        eventPublisher().publish(BookAvailableEvent(isbn, bookId))
    }

    override fun remove(bookId: String) {
        if (!this.bookIds.contains(bookId)) {
            return
        }
        this.bookIds.remove(bookId)
        eventPublisher().publish(BookUnavailableEvent(isbn, bookId))
    }

    override fun isEmpty(): Boolean {
        return this.bookIds.isEmpty()
    }

    override fun chooseBookRandomly(): String {
        return this.bookIds.random()
    }
}

interface AvailableBooksRepository {
    fun findByIdOrError(isbn: String): AvailableBooks
    fun save(availableBooks: AvailableBooks)
}

class BookUnavailableEvent(
    val isbn: String,
    val bookId: String
): DomainEvent

class BookAvailableEvent(
    val isbn: String,
    val bookId: String
): DomainEvent
