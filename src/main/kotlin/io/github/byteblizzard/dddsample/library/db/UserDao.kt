package io.github.byteblizzard.dddsample.library.db

import io.github.byteblizzard.dddsample.library.domain.user.UserId
import io.github.byteblizzard.dddsample.library.domain.user.UserImpl
import org.springframework.data.jpa.repository.JpaRepository

interface UserDao: JpaRepository<UserImpl, UserId> {
}