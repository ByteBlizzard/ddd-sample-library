package io.github.byteblizzard.dddsample.library.domain.book

import org.springframework.stereotype.Service

class StockInBookCmd(
    val bookId: String,
    val isbn: String,
    val name: String,
    val picture: String,
    val description: String
)

@Service
class StockInBookCmdHandler(
    private val BookRepository: BookRepository,
    private val BookFactory: BookFactory
) {
    fun handle(cmd: StockInBookCmd) {
        val book = this.BookFactory.create(cmd)
        BookRepository.save(book)
    }
}