package io.github.byteblizzard.dddsample.library.domain.book

import io.github.byteblizzard.dddsample.library.db.BookDao
import io.github.byteblizzard.dddsample.library.domain.DomainException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class BookRepositoryImpl(
    private val bookDao: BookDao
) : BookRepository {
    override fun findByIdOrError(bookId: String): Book {
        return bookDao.findByIdOrNull(bookId) ?: throw DomainException("找不到书，id: $bookId")
    }

    override fun findByBookId(bookId: String): Book? {
        return bookDao.findByIdOrNull(bookId)
    }

    override fun save(book: Book) {
        this.bookDao.save(book as? BookImpl ?: throw IllegalArgumentException())
    }
}