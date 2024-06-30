package io.github.byteblizzard.dddsample.library.domain.lend

import io.github.byteblizzard.dddsample.library.domain.user.UserId
import org.springframework.stereotype.Component

class LendOutCmd(val bookId: String, val lendOutUserId: UserId)

@Component
class LendOutCmdHandler(
    private val lendFactory: LendFactory,
    private val lendRepository: LendRepository
) {
    fun handle(cmd: LendOutCmd) {
        val lend = this.lendFactory.create(cmd)
        this.lendRepository.save(lend)
    }
}