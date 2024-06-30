package io.github.byteblizzard.dddsample.library.domain.lend

import org.springframework.stereotype.Component

class TryReturnBookOverdueCmd(
    val lendId: LendId
)

@Component
class TryReturnBookOverdueCmdHandler(
    val lendRepository: LendRepository
) {
    fun handle(cmd: TryReturnBookOverdueCmd) {
        val lend = lendRepository.findByIdOrError(cmd.lendId)
        lend.tryOverdue()
        lendRepository.update(lend)
    }

}