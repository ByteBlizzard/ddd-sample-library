package io.github.byteblizzard.dddsample.library.domain.book

import io.github.byteblizzard.dddsample.library.domain.DomainException
import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.DomainRegistry.eventPublisher
import org.springframework.stereotype.Component

@Component
class BookFactory(
    private val BookRepository: BookRepository
) {

    fun create(cmd: StockInBookCmd): Book {
        val existingBookWithSameId = this.BookRepository.findByBookId(cmd.bookId)
        if (existingBookWithSameId != null) {
            throw DuplicateBookIdException()
        }

        val newBook = BookImpl(
            bookId = cmd.bookId,
            isbn = cmd.isbn,
            onShelf = false
        )
        eventPublisher().publish(BookStockedInEvent(
            bookId = cmd.bookId,
            isbn = cmd.isbn,
            name = cmd.name,
            picture = cmd.picture,
            description = cmd.description
        ))
        return newBook
    }
}

class DuplicateBookIdException : DomainException("书名重复了")

class BookStockedInEvent(
    val bookId: String,
    val isbn: String,
    val name: String,
    val picture: String,
    val description: String
): DomainEvent