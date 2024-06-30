package io.github.byteblizzard.dddsample.library.domain.lend

import io.github.byteblizzard.dddsample.library.domain.book.BookRepository
import org.springframework.stereotype.Component

class ReturnBookCmd(
    val bookId: String
)

@Component
class ReturnBookCmdHandler (
    private val bookRepository: BookRepository,
    private val lendRepository: LendRepository
) {
   fun handle(cmd: ReturnBookCmd) {
       bookRepository.findByIdOrError(cmd.bookId)
       val lend = lendRepository.findLatestByBookIdOrError(cmd.bookId)
       lend.returnBack()
       lendRepository.update(lend)
   }
}