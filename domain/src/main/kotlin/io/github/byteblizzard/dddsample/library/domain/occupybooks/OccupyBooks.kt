package io.github.byteblizzard.dddsample.library.domain.occupybooks

import io.github.byteblizzard.dddsample.library.domain.user.UserId
import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.DomainRegistry.eventPublisher
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.springframework.stereotype.Component

interface OccupyBooks {
    fun modify(increment: Int)
    fun countOfOccupiedBooks(): Int
}

@Entity
@Table(name = "t_occupy_books")
class OccupyBooksImpl(
    @EmbeddedId
    val userId: UserId,

    var occupyCount: Int
): OccupyBooks {
    override fun modify(increment: Int) {
        val oldOccupyCount = this.occupyCount
        this.occupyCount += increment
        if (this.occupyCount < 0) {
            this.occupyCount = 0
        }
        eventPublisher().publish(OccupyBooksChangedEvent(this.userId, this.occupyCount, oldOccupyCount))
    }

    override fun countOfOccupiedBooks(): Int {
        return this.occupyCount
    }

}

interface OccupyBooksRepository {
    fun findByIdOrError(userId: UserId): OccupyBooks
    fun save(occupyBooks: OccupyBooks)
}

class OccupyBooksChangedEvent(
    val userId: UserId,
    val newOccupyCount: Int,
    val oldOccupyCount: Int
): DomainEvent