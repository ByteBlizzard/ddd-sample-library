package io.github.byteblizzard.dddsample.library.domain.book

import org.springframework.stereotype.Service

class TakeOffShelfCmd(
    val bookId: String
)

@Service
class TakeOffShelfHandler(
    private val BookRepository: BookRepository
) {
    fun handle(cmd: TakeOffShelfCmd) {
        val book = this.BookRepository.findByIdOrError(cmd.bookId)
        book.takeOffShelf()
        BookRepository.save(book)
    }

}