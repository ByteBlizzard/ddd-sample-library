package io.github.byteblizzard.dddsample.library.domain.occupybooks

import io.github.byteblizzard.dddsample.library.domain.user.UserId
import org.springframework.stereotype.Component

class ModifyUserOccupyBooksCmd(
    val userId: UserId,
    val increment: Int
)

@Component
class ModifyUserOccupyBooksCmdHandler(
    val occupyBooksRepository: OccupyBooksRepository
) {

    fun handle(cmd: ModifyUserOccupyBooksCmd) {
        val occupyBooks = this.occupyBooksRepository.findByIdOrError(cmd.userId)
        occupyBooks.modify(cmd.increment)
        this.occupyBooksRepository.save(occupyBooks)
    }
}