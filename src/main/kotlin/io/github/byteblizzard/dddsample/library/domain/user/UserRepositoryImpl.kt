package io.github.byteblizzard.dddsample.library.domain.user

import io.github.byteblizzard.dddsample.library.db.UserDao
import io.github.byteblizzard.dddsample.library.domain.DomainException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserRepositoryImpl(
    private val userDao: UserDao
): UserRepository {
    override fun findByIdOrError(userId: UserId): User {
        return this.userDao.findByIdOrNull(userId) ?: throw DomainException("找不到用户, id: ${userId.value}")
    }

    override fun save(user: User) {
        this.userDao.save(user as? UserImpl ?: throw IllegalArgumentException())
    }
}