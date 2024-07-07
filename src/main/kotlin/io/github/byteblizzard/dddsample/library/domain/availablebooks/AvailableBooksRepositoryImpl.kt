package io.github.byteblizzard.dddsample.library.domain.availablebooks

import io.github.byteblizzard.dddsample.library.db.AvailableBooksDao
import org.springframework.stereotype.Component

@Component
class AvailableBooksRepositoryImpl(
    private val availableBooksDao: AvailableBooksDao
) : AvailableBooksRepository {
    override fun findById(isbn: String): AvailableBooks {
        return this.availableBooksDao.findById(isbn).orElse(AvailableBooksImpl(isbn = isbn))
    }

    override fun save(availableBooks: AvailableBooks) {
        this.availableBooksDao.save(availableBooks as? AvailableBooksImpl ?: throw IllegalArgumentException())
    }
}