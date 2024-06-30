package io.github.byteblizzard.dddsample.library.domain.lend

import io.github.byteblizzard.dddsample.library.db.LendDao
import io.github.byteblizzard.dddsample.library.domain.DomainException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class LendRepositoryImpl(
    private val lendDao: LendDao,
) : LendRepository {
    override fun save(lend: Lend) {
        this.lendDao.save(lend as? LendImpl ?: throw IllegalArgumentException())
    }

    override fun update(lend: Lend) {
        this.save(lend)
    }

    override fun findLatestByBookIdOrError(bookId: String): Lend {
        return this.lendDao.findFirst1ByBookIdOrderByLendOutTimeDesc(bookId)
            ?: throw DomainException("找不到书的借出信息，bookId: $bookId")
    }

    override fun findByIdOrError(lendId: LendId): Lend {
        return this.lendDao.findByIdOrNull(lendId) ?: throw DomainException("找不到借出, id: ${lendId.value}")
    }
}