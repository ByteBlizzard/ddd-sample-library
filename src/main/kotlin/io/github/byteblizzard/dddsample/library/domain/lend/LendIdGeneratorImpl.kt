package io.github.byteblizzard.dddsample.library.domain.lend

import org.springframework.stereotype.Component

@Component
class LendIdGeneratorImpl : LendIdGenerator {
    private var count: Long = 0
    override fun nextLendId(): LendId {
        count++
        return LendId("$count")
    }
}