package io.github.byteblizzard.dddsample.library.domain.user

import io.github.byteblizzard.dddsample.library.domain.DomainEvent
import io.github.byteblizzard.dddsample.library.domain.DomainRegistry.eventPublisher
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

interface User {
    fun increaseOverdueTimes()
    fun enable()
    val suspended: Boolean
}

@Entity
@Table(name = "t_user")
class UserImpl(
    @EmbeddedId
    private val userId: UserId,

    var overdueTimes: Int,

    override var suspended: Boolean
): User {

    override fun increaseOverdueTimes() {
        this.overdueTimes++
        eventPublisher().publish(UserReturnBookOverdueEvent(this.userId))
        if (this.overdueTimes == 3 && !this.suspended) {
            this.suspended = true
            eventPublisher().publish(UserSuspendedEvent(this.userId))
        }
    }

    override fun enable() {
        if (!this.suspended) {
            return
        }

        this.suspended = false
        this.overdueTimes = 0
        eventPublisher().publish(UserEnabledEvent(this.userId))
    }
}

interface UserRepository {
    fun findByIdOrError(userId: UserId): User
    fun save(user: User)
}

class UserSuspendedEvent(val userId: UserId): DomainEvent

class UserReturnBookOverdueEvent(val userId: UserId): DomainEvent
class UserEnabledEvent(val userId: UserId): DomainEvent
class UserCreatedEvent(val userId: UserId, val name: String): DomainEvent

