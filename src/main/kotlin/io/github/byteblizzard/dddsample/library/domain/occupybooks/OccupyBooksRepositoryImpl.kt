package io.github.byteblizzard.dddsample.library.domain.occupybooks

import io.github.byteblizzard.dddsample.library.db.OccupyBooksDao
import io.github.byteblizzard.dddsample.library.domain.DomainException
import io.github.byteblizzard.dddsample.library.domain.user.UserId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class OccupyBooksRepositoryImpl(
    private val occupyBooksDao: OccupyBooksDao
): OccupyBooksRepository {
    override fun findByIdOrError(userId: UserId): OccupyBooks {
        return this.occupyBooksDao.findByIdOrNull(userId)
            ?: throw DomainException("找不到占用书聚合, id: ${userId.value}")
    }

    override fun save(occupyBooks: OccupyBooks) {
        this.occupyBooksDao.save(occupyBooks as? OccupyBooksImpl ?: throw IllegalArgumentException())
    }
}