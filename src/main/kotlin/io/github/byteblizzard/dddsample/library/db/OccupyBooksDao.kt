package io.github.byteblizzard.dddsample.library.db

import io.github.byteblizzard.dddsample.library.domain.occupybooks.OccupyBooksImpl
import io.github.byteblizzard.dddsample.library.domain.user.UserId
import org.springframework.data.jpa.repository.JpaRepository

interface OccupyBooksDao: JpaRepository<OccupyBooksImpl, UserId> {
}