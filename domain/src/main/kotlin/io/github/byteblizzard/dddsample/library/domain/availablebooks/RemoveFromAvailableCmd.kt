package io.github.byteblizzard.dddsample.library.domain.availablebooks

import org.springframework.stereotype.Component

class RemoveFromAvailableCmd (
    val isbn: String,
    val bookId: String
)

@Component
class RemoveFromAvailableCmdHandler(
    val availableBooksRepository: AvailableBooksRepository
) {
    fun handle(cmd: RemoveFromAvailableCmd) {
        val availableBooks = this.availableBooksRepository.findByIdOrError(cmd.isbn)
        availableBooks.remove(cmd.bookId)
        this.availableBooksRepository.save(availableBooks)
    }
}