package io.github.byteblizzard.dddsample.library.db

import io.github.byteblizzard.dddsample.library.domain.lend.LendId
import io.github.byteblizzard.dddsample.library.domain.lend.LendImpl
import org.springframework.data.jpa.repository.JpaRepository

interface LendDao: JpaRepository<LendImpl, LendId> {
    fun findFirst1ByBookIdOrderByLendOutTimeDesc(bookId: String) : LendImpl?
}