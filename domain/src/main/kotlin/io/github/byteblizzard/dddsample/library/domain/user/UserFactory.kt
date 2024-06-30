package io.github.byteblizzard.dddsample.library.domain.user

import io.github.byteblizzard.dddsample.library.domain.DomainRegistry.eventPublisher
import org.springframework.stereotype.Component

@Component
class UserFactory {
    fun create(cmd: CreateUserCmd): User {
        eventPublisher().publish(UserCreatedEvent(userId = cmd.userId, name = cmd.name))
        return UserImpl(
            userId = cmd.userId,
            suspended = false,
            overdueTimes = 0
        )
    }
}