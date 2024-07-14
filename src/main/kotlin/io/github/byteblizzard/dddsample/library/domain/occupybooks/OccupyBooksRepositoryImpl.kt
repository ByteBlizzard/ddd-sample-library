package io.github.byteblizzard.dddsample.library.domain.occupybooks

import io.github.byteblizzard.dddsample.library.db.OccupyBooksDao
import io.github.byteblizzard.dddsample.library.domain.user.UserId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class OccupyBooksRepositoryImpl(
    private val occupyBooksDao: OccupyBooksDao
): OccupyBooksRepository {
    override fun findById(userId: UserId): OccupyBooks {
        return this.occupyBooksDao.findByIdOrNull(userId) ?: OccupyBooksImpl(
            userId = userId,
            occupyCount = 0
        )
    }

    override fun save(occupyBooks: OccupyBooks) {
        this.occupyBooksDao.save(occupyBooks as? OccupyBooksImpl ?: throw IllegalArgumentException())
    }
}