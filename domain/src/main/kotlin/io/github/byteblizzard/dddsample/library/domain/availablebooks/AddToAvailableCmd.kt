package io.github.byteblizzard.dddsample.library.domain.availablebooks

import org.springframework.stereotype.Component

class AddToAvailableCmd(
    val isbn: String,
    val bookId: String
)

@Component
class AddToAvailableCmdHandler(
    val availableBooksRepository: AvailableBooksRepository
) {
    fun handle(cmd: AddToAvailableCmd) {
        val availableBooks = this.availableBooksRepository.findById(cmd.isbn)
        availableBooks.add(cmd.bookId)
        this.availableBooksRepository.save(availableBooks)
    }
}