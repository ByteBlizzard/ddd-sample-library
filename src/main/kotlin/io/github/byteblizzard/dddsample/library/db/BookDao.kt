package io.github.byteblizzard.dddsample.library.db

import io.github.byteblizzard.dddsample.library.domain.book.BookImpl
import org.springframework.data.jpa.repository.JpaRepository

interface BookDao: JpaRepository<BookImpl, String> {
}