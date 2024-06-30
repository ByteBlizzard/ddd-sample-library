package io.github.byteblizzard.dddsample.library.domain.book

import org.springframework.stereotype.Service

class PutOnShelfCmd(
    val bookId: String
)

@Service
class PutOnShelfCmdHandler(
    private val BookRepository: BookRepository
) {
    fun handle(cmd: PutOnShelfCmd) {
        val book = this.BookRepository.findByIdOrError(cmd.bookId)
        book.putOnShelf()
        BookRepository.save(book)
    }

}