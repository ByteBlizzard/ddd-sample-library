package io.github.byteblizzard.dddsample.library.domain.availablebooks

import io.github.byteblizzard.dddsample.library.db.AvailableBooksDao
import io.github.byteblizzard.dddsample.library.domain.DomainException
import org.springframework.stereotype.Component

@Component
class AvailableBooksRepositoryImpl(
    private val availableBooksDao: AvailableBooksDao
) : AvailableBooksRepository {
    override fun findByIdOrError(isbn: String): AvailableBooks {
        return this.availableBooksDao.findById(isbn).orElseThrow {DomainException("找不到可用书聚合，id: $isbn")}
    }

    override fun save(availableBooks: AvailableBooks) {
        this.availableBooksDao.save(availableBooks as? AvailableBooksImpl ?: throw IllegalArgumentException())
    }
}