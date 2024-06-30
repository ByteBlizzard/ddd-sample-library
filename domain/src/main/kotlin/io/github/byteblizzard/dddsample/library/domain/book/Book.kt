package io.github.byteblizzard.dddsample.library.domain.book

import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.DomainRegistry.eventPublisher
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

interface Book {
    fun putOnShelf()
    fun takeOffShelf()
    fun isOnShelf(): Boolean
    val isbn: String
}

@Entity
@Table(name = "t_book")
class BookImpl(
    @Id
    val bookId: String,
    override val isbn: String,
    var onShelf: Boolean
): Book {
    override fun putOnShelf() {
        if (onShelf) {
            return
        }

        this.onShelf = true
        eventPublisher().publish(BookPutOnShelfEvent(this.bookId))
    }

    override fun takeOffShelf() {
        if (!onShelf) {
            return
        }

        this.onShelf = false
        eventPublisher().publish(BookTakenOffShelfEvent(this.bookId))
    }

    override fun isOnShelf(): Boolean {
        return this.onShelf
    }

}

interface BookRepository {
    fun findByIdOrError(bookId: String): Book
    fun findByBookId(bookId: String): Book?
    fun save(book: Book)
}

class BookPutOnShelfEvent(val bookId: String): DomainEvent
class BookTakenOffShelfEvent(val bookId: String): DomainEvent
